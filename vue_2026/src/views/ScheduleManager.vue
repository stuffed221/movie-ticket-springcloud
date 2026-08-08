<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"><el-icon><Grid /></el-icon></i> 排片管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="form.scheduleMovieName" placeholder="影片名" class="handle-input mr10"></el-input>
                
                <el-button type="primary" icon="Search" @click="detailSearch">搜索</el-button>
                <el-button type="primary" icon="Search" @click="detail">详细筛选</el-button>
                <el-button type="primary" icon="Refresh" @click="reseter">重置筛选</el-button>
                <el-button type="info" icon="right" @click="addschedule">新增排片信息</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="scheduleId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="scheduleMovieName" label="电影名称"></el-table-column>
                <el-table-column prop="scheduleCinemaName" label="影院名称"></el-table-column>
                <el-table-column prop="scheduleHall" label="影厅号"></el-table-column>
                <el-table-column prop="schedulePrice" label="票价"></el-table-column>
                <el-table-column prop="scheduleShowTime" label="放映时间"></el-table-column>
                <el-table-column prop="scheduleRemainseats" label="剩余座位"></el-table-column>
                <el-table-column prop="scheduleUpdateTime" label="更新时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        
                        <el-button type="text" icon="EditPen" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
                        <el-button type="text" icon="Delete" class="red" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 详细筛选框 -->
        <el-dialog title="详细筛选" v-model="detailform" width="30%">
            <el-form label-width="70px">
                <el-form-item label="影片名">
                    <el-input v-model="form.scheduleMovieName"></el-input>
                </el-form-item>
                <el-form-item label="影院名">
                    <el-input v-model="form.scheduleCinemaName"></el-input>
                </el-form-item>
                <el-form-item label="影厅号">
                    <el-input v-model="form.scheduleHall"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="detailform = false">取 消</el-button>
                    <el-button type="primary" @click="detailSearch">确 定</el-button>
                </span>
            </template>
        </el-dialog>    
            

        <!-- 增加排片 -->
        <el-dialog title="增加排片" v-model="scheduleadd" width="30%">
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="电影">
                    <el-select v-model="update.scheduleMovieName" placeholder="选择电影"
                        @change="(val) => {
                        const m = movieList.find(i => i.movieName === val);
                        update.scheduleMovieId = m ? m.movieId : null;
                        }">
                    <el-option v-for="m in movieList" :key="m.movieId"
                        :label="m.movieName" :value="m.movieName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="影院">
                    <el-select v-model="update.scheduleCinemaName" placeholder="选择影院"
                        @change="(val) => {
                        const m = cinemaList.find(i => i.cinemaName === val);
                        update.scheduleCinemaId = m ? m.cinemaId : null;
                        }">
                    <el-option v-for="m in cinemaList" :key="m.cinemaId"
                        :label="m.cinemaName" :value="m.cinemaName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="影厅号">
                    <el-input v-model="update.scheduleHall" />
                </el-form-item>
                <el-form-item label="票价">
                    <el-input v-model="update.schedulePrice" />
                </el-form-item>
                <el-form-item label="放映时间">
                    <el-date-picker
                        v-model="update.scheduleShowTime"
                        type="datetime"
                        placeholder="选择放映时间"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                </el-form-item>
                <el-form-item label="座位数">
                    <el-input v-model="update.scheduleRemainseats" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="scheduleadd = false">取 消</el-button>
                    <el-button type="primary" @click="addaschedule">确 定</el-button>
                </span>
            </template>
        </el-dialog>    


        <!-- 更新排片 -->
        <el-dialog title="更新排片" v-model="scheduleupdate" width="30%">
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="电影">
                    <el-select v-model="update.scheduleMovieName" placeholder="选择电影"
                        @change="(val) => {
                        const m = movieList.find(i => i.movieName === val);
                        update.scheduleMovieId = m ? m.movieId : null;
                        }">
                    <el-option v-for="m in movieList" :key="m.movieId"
                        :label="m.movieName" :value="m.movieName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="影院">
                    <el-select v-model="update.scheduleCinemaName" placeholder="选择影院"
                        @change="(val) => {
                        const m = cinemaList.find(i => i.cinemaName === val);
                        update.scheduleCinemaId = m ? m.cinemaId : null;
                        }">
                    <el-option v-for="m in cinemaList" :key="m.cinemaId"
                        :label="m.cinemaName" :value="m.cinemaName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="影厅号">
                    <el-input v-model="update.scheduleHall" />
                </el-form-item>
                <el-form-item label="票价">
                    <el-input v-model="update.schedulePrice" />
                </el-form-item>
                <el-form-item label="放映时间">
                    <el-date-picker
                        v-model="update.scheduleShowTime"
                        type="datetime"
                        placeholder="选择放映时间"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                </el-form-item>
                <el-form-item label="剩余座位">
                    <el-input v-model="update.scheduleRemainseats" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="scheduleupdate = false">取 消</el-button>
                    <el-button type="primary" @click="Editon">确 定</el-button>
                </span>
            </template>
        </el-dialog>    

    </div>

</template>

        

<script setup>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";
import {useRouter} from "vue-router";

        const form = reactive({
            scheduleId: null,
            scheduleMovieId: null,
            scheduleMovieName: '',
            scheduleCinemaId: null,
            scheduleCinemaName: '',
            scheduleHall: null,
            schedulePrice: null,
            scheduleShowTime: '',
            scheduleRemainseats: null,
            scheduleCreateTime: '',
            scheduleUpdateTime: ''
        });

        const add = reactive({
            scheduleId: null,
            scheduleMovieId: null,
            scheduleMovieName: '',
            scheduleCinemaId: null,
            scheduleCinemaName: '',
            scheduleHall: null,
            schedulePrice: null,
            scheduleShowTime: '',
            scheduleRemainseats: null
        })

        const update = reactive({
            scheduleId: null,
            scheduleMovieId: null,
            scheduleMovieName: '',
            scheduleCinemaId: null,
            scheduleCinemaName: '',
            scheduleHall: null,
            schedulePrice: null,
            scheduleShowTime: '',
            scheduleRemainseats: null
        })

        const query = reactive({
            pageIndex: 1,
            pageSize: 2,
            pageTotal: 2,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        // 获取表格数据
        const getData = () => {
            // fetchData(query).then((res) => {
            //     tableData.value = res.list;
            //     pageTotal.value = res.pageTotal || 50;
            // });
            //从后端拿数据
            var path="http://localhost:8080/schedule/page?npage="+query.pageIndex;
            axios.post(path,form).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        var pageInfo = result.data;
                        tableData.value = pageInfo.records;
                        query.pageIndex=pageInfo.current;
                        query.pageTotal=pageInfo.total;
                        query.pageSize=pageInfo.size;
                    }
                }
            });
            


        };
        getData();

        const detailSearch = ()=>{
            getData();
            detailform.value = false;
        }

        const router = useRouter();

        const addaschedule = () => {
            if(add.scheduleMovieName == '' ||
               add.scheduleCinemaName == '' ||
               add.scheduleHall == null ||
               add.schedulePrice == null ||
               add.scheduleShowTime == '' ||
               add.scheduleRemainseats == null)
            {
                scheduleadd.value = false;
                for (const key in add) {
                    delete add[key];
                }   
                ElMessage.error("信息不完善，添加失败！");
                return;
            }
            
            var path = "http://localhost:8080/schedule/insert";
            axios.post(path, add).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        ElMessage.success("添加成功");
                    }
                }
            });

            scheduleadd.value = false;
            for (const key in add) {
                delete add[key];
            }   
        };

        const detail = ()=>{
            detailform.value = true;
        }

        const addschedule = ()=>{
            scheduleadd.value = true;
        }

        const handleEdit = (index, row) =>{
            scheduleupdate.value = true;
            idx = index;
            Object.keys(update).forEach((item) => {
                update[item] = row[item];
            });
        }

        const reseter = ()=>{
            form.scheduleMovieName = '';
            form.scheduleCinemaName = '';
            form.scheduleHall = null;
            getData();
        }

        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };

        const handleDelete = (index, row) => {
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    var path = "http://localhost:8080/schedule/delete?id=" + row.scheduleId;
                    axios.get(path).then(function(obj){
                        if(obj.status == 200){
                            var result = obj.data;
                            if(result.code == 200){
                                location.reload();
                                ElMessage.success("删除成功");
                            }
                        }
                    });
                })
                .catch(() => {});
        };

        const editVisible = ref(false);
        const detailform = ref(false);
        const scheduleadd = ref(false);
        const scheduleupdate = ref(false);
        
        let idx = -1;

        const Editon = () => {
            var path = "http://localhost:8080/schedule/update";
            axios.post(path, update).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        location.reload();
                        ElMessage.success("更新成功");
                    }
                }
            });

            scheduleupdate.value = false;
            for (const key in update) {
                delete update[key];
            }   
        };

        const movieList = ref([]);
        const cinemaList = ref([]);

        const loadMovies = async () => {
        const res = await axios.get("http://localhost:8080/movie/list");
        movieList.value = res.data.data;
        };
        loadMovies();
        const loadCinemas = async () => {
            const res = await axios.get("http://localhost:8080/cinema/list");
            cinemaList.value = res.data.data;
        };
        loadCinemas();
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
