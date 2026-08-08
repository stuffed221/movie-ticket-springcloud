<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"><el-icon><Grid /></el-icon></i> 管理员名单
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="form.movieName" placeholder="影片名" class="handle-input mr10"></el-input>
                
                <el-button type="primary"  icon="Search" @click="detailSearch">搜索</el-button>
                <el-button type="primary"  icon="Search" @click="detail">详细筛选</el-button>
                <el-button type="primary"  icon="Refresh" @click="reseter">重置筛选</el-button>
                <el-button type="info"  icon="right" @click="addmovie">新增影片信息</el-button>

                
                
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="movieId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="movieName" label="电影名称"></el-table-column>
                <el-table-column prop="movieActor" label="演员"></el-table-column>
                <el-table-column prop="movieType" label="电影类型"></el-table-column>
                <el-table-column prop="movieLanguage" label="语言"></el-table-column>
                <el-table-column prop="movieTime" label="时长（分钟）"></el-table-column>
                <el-table-column prop="releaseTime" label="上映时间"></el-table-column>
                <el-table-column prop="movieIntroduction" width="250" label="简介"></el-table-column>
                <el-table-column label="海报" width="200" align="center">
                    <template #default="scope">
                        <img v-if="scope.row.moviePoster" :src="scope.row.moviePoster" class="table-td-thumb" />
                        <span v-else style="color:#ccc;font-size:12px">暂无</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template #default="scope">
                        <div class="btn-column">
                        <el-button type="text" icon="CirclePlus" style="color:#67c23a" @click="handleSchedule(scope.$index,scope.row)">排片</el-button>
                        <el-button type="text" icon="EditPen" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
                        <el-button type="text" icon="Delete" class="red" @click="handleDelete(scope.$index,scope.row)">注销</el-button>
                        </div>
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
                    <el-input v-model="form.movieName"></el-input>
                </el-form-item>
                <el-form-item label="演员">
                    <el-input v-model="form.movieActor"></el-input>
                </el-form-item>
                <el-form-item label="电影类型">
                    <el-select v-model="form.movieType" placeholder="请选择影片类型">
                        <el-option label="无" value="" />
                        <el-option label="剧情" value="剧情" />
                        <el-option label="犯罪" value="犯罪" />
                        <el-option label="动画" value="动画" />
                        <el-option label="运动" value="运动" />
                        <el-option label="爱情" value="爱情" />
                        <el-option label="喜剧" value="喜剧" />
                        <el-option label="奇幻" value="奇幻" />
                        <el-option label="刑侦" value="刑侦" />
                        <el-option label="惊悚" value="惊悚" />
                        <el-option label="励志" value="励志" />
                        <el-option label="历史" value="历史" />
                        <el-option label="体育" value="体育" />
                    </el-select>
                </el-form-item>
                <el-form-item label="语种">
                    <el-select v-model="form.movieLanguage" placeholder="请选择影片语种">
                        <el-option label="无" value="" />
                        <el-option label="国语" value="国语" />
                        <el-option label="英语" value="英语" />
                        <el-option label="日语" value="日语" />
                        <el-option label="粤语" value="粤语" />
                        <el-option label="德语" value="德语" />
                    </el-select>
                </el-form-item>
                <!-- <el-form-item label="演员">
                    <el-input v-model="form.movie"></el-input>

                    
                </el-form-item>
                <el-form-item label="演员">
                    <el-input v-model="form."></el-input>
                </el-form-item> -->
                <el-form-item label="时长">
                    <el-select v-model="form.movieTimeRange" placeholder="请选择时长范围">
                        <el-option label="无" value="" />
                        <el-option label="60分钟内" value="1" />
                        <el-option label="60~90分钟" value="2" />
                        <el-option label="90~120分钟" value="3" />
                        <el-option label="120分钟以上" value="4" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="detailform = false">取 消</el-button>
                    <el-button type="primary" @click="detailSearch">确 定</el-button>
                </span>
            </template>
        </el-dialog>    
            

        <!-- 增加影片 -->
        <el-dialog title="增加影片" v-model="movieadd" width="30%">
            
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="影片名">
                        <el-input v-model="add.movieName" />
                    </el-form-item>
                    <el-form-item label="演员">
                        <el-input v-model="add.movieActor" />
                    </el-form-item>
                    <el-form-item label="类型">
                        <el-input v-model="add.movieType" />
                    </el-form-item>
                    <el-form-item label="语种">
                        <el-input v-model="add.movieLanguage" />
                    </el-form-item>
                    <el-form-item label="时长">
                        <el-input v-model="add.movieTime" />
                    </el-form-item>
                    <el-form-item label="上映时间">
      <el-col :span="11">
        <el-date-picker
          v-model="add.releaseTime"
          type="date"
          placeholder="Pick a date"
          style="width: 100%"
        />
      </el-col>
      
      
    </el-form-item>
                    <el-form-item label="简介">
                        <el-input v-model="add.movieIntroduction" type="textarea" />
                    </el-form-item>
                    <el-form-item label="海报URL">
                        <el-input v-model="add.moviePoster" placeholder="输入OSS图片URL" />
                    </el-form-item>
                   
                
                </el-form>
            
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="movieadd = false">取 消</el-button>
                    <el-button type="primary" @click="addamovie">确 定</el-button>
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


        <!-- 更新表 -->
        <el-dialog title="更新影片" v-model="movieupdate" width="30%">
            
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="影片名">
                    <el-input v-model="update.movieName" />
                </el-form-item>
                <el-form-item label="演员">
                    <el-input v-model="update.movieActor" />
                </el-form-item>
                <el-form-item label="类型">
                    <el-input v-model="update.movieType" />
                </el-form-item>
                <el-form-item label="语种">
                    <el-input v-model="update.movieLanguage" />
                </el-form-item>
                <el-form-item label="时长">
                    <el-input v-model="update.movieTime" />
                </el-form-item>
                <el-form-item label="上映时间">
  <el-col :span="11">
    <el-date-picker
      v-model="update.releaseTime"
      type="date"
      placeholder="Pick a date"
      style="width: 100%"
    />
  </el-col>
  
  
</el-form-item>
                
                <el-form-item label="简介">
                    <el-input v-model="update.movieIntroduction" type="textarea" />
                </el-form-item>
                <el-form-item label="海报URL">
                    <el-input v-model="update.moviePoster" placeholder="输入OSS图片URL" />
                </el-form-item>
               
            
            </el-form>
        
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="movieupdate = false">取 消</el-button>
                <el-button type="primary" @click="Editon">确 定</el-button>
            </span>
        </template>
    </el-dialog>    


        <!-- 排片弹窗 -->
        <el-dialog title="新增排片" v-model="scheduleadd" width="30%">
            <el-form label-width="80px">
                <el-form-item label="电影名称">
                    <el-input v-model="scheduleform.scheduleMovieName" disabled />
                </el-form-item>
                <el-form-item label="影院名称">
                    <el-select v-model="scheduleform.scheduleCinemaName" placeholder="选择影院"
                        @change="(val) => {
                            const c = cinemaList.find(i => i.cinemaName === val);
                            scheduleform.scheduleCinemaId = c ? c.cinemaId : null;
                        }">
                        <el-option v-for="c in cinemaList" :key="c.cinemaId"
                            :label="c.cinemaName" :value="c.cinemaName" />
                    </el-select>
                </el-form-item>
                <el-form-item label="影厅号">
                    <el-input v-model="scheduleform.scheduleHall" />
                </el-form-item>
                <el-form-item label="票价">
                    <el-input v-model="scheduleform.schedulePrice" />
                </el-form-item>
                <el-form-item label="放映时间">
                    <el-date-picker
                        v-model="scheduleform.scheduleShowTime"
                        type="datetime"
                        placeholder="选择放映时间"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                </el-form-item>
                <el-form-item label="座位数">
                    <el-input v-model="scheduleform.scheduleRemainseats" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="scheduleadd = false">取 消</el-button>
                    <el-button type="primary" @click="submitSchedule">确 定</el-button>
                </span>
            </template>
        </el-dialog>

    </div>

</template>

        

<script setup>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchData } from "../api/index";
import axios from "axios";
import {useRouter} from "vue-router";

// export default {
//     name: "basetable",
//     setup() {

        const cinemaList = ref([]);
        const loadCinemas = async () => {
            const res = await axios.get("http://localhost:8080/cinema/list");
            cinemaList.value = res.data.data;
        };
        loadCinemas();

        const scheduleform = reactive({
            scheduleMovieId: null,
            scheduleMovieName: '',
            scheduleCinemaId: null,
            scheduleCinemaName: '',
            scheduleHall: null,
            schedulePrice: null,
            scheduleShowTime: '',
            scheduleRemainseats: null
        });
        const scheduleadd = ref(false);

        const handleSchedule = (index, row) => {
            scheduleform.scheduleMovieId = row.movieId;
            scheduleform.scheduleMovieName = row.movieName;
            scheduleadd.value = true;
        };

        const submitSchedule = () => {
            if(scheduleform.scheduleCinemaName == '' ||
               scheduleform.scheduleHall == null ||
               scheduleform.schedulePrice == null ||
               scheduleform.scheduleShowTime == '' ||
               scheduleform.scheduleRemainseats == null)
            {
                scheduleadd.value = false;
                for (const key in scheduleform) {
                    delete scheduleform[key];
                }
                ElMessage.error("信息不完善，添加失败！");
                return;
            }
            axios.post("http://localhost:8080/schedule/insert", scheduleform).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        ElMessage.success("排片成功");
                    }
                }
            });
            scheduleadd.value = false;
            for (const key in scheduleform) {
                delete scheduleform[key];
            }
        };


        const form = reactive({
            movieId:null,
            movieName:'',
            movieActor:'',
            movieType:'',
            movieLanguage:'',
            

        });

        const add = reactive({
            movieId:null,
            movieName:'',
            movieActor:'',
            movieType:'',
            movieLanguage:'',
            movieTime:null,
            releaseTime:'',
            moviePrice:'',
            movieIntroduction:'',
            moviePoster:''
        })

        const update = reactive({
            movieId:null,
            movieName:'',
            movieActor:'',
            movieType:'',
            movieLanguage:'',
            movieTime:null,
            releaseTime:'',
            moviePrice:'',
            movieIntroduction:'',
            moviePoster:''
        })

        const delete1 = reactive({
            movieId:null,
            movieName:'',
            movieActor:'',
            movieType:'',
            movieLanguage:'',
            movieTime:null,
            releaseTime:'',
            moviePrice:'',
            movieIntroduction:'',
            moviePoster:''
        })


        const query = reactive({
            
            pageIndex: 1,
            pageSize: 2,
            pageTotal:2,

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
            var path="http://localhost:8080/movie/page?npage="+query.pageIndex;
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
            var kk= form;
            getData();
            detailform.value = false;

        }

        const router= useRouter();

        

       
        const addamovie = () => {
            // fetchData(query).then((res) => {
            //     tableData.value = res.list;
            //     pageTotal.value = res.pageTotal || 50;
            // });
            //从后端拿数据
            
                if(add.movieName==''||
            add.movieActor==''||
            add.movieType==''||
            add.movieLanguage==''||
            add.movieTime==null||
            add.releaseTime==''||
            add.moviePrice==''||
            add.movieIntroduction=='')
                {
                    movieadd.value = false;
                    for (const key in add) {
                        delete add[key];
                    }   
                    ElMessage.error("信息不完善，添加失败！");
                    
                }
                   
            

            
            var path="http://localhost:8081/cinema/movie/insert";
            axios.post(path,add).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        ElMessage.success("添加成功");
                    }
                }
            });

            
            movieadd.value = false;
            for (const key in add) {
                delete add[key];
            }   
            


        };

        const detail = ()=>{
            detailform.value = true;

        }

        const addmovie = ()=>{
            movieadd.value = true;

        }

        const handleEdit = (index, row) =>{
            movieupdate.value = true;
            idx = index;
            Object.keys(update).forEach((item) => {
                update[item] = row[item];
            });
            

        }

    

        const reseter = ()=>{
            form.movieActor = '',
            form.movieLanguage = '',
            form.movieName= '',
            form.movieType = '';
            getData();
        }

      
        

        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getData();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };

        const manageradd = ()=>{
            router.push("/manageradd")
        }

        const managerlogin = ()=>{
            router.push("/managerlogin")
        }

        // 删除操作
        const handleDelete = (index,row) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    idx = index;
                    Object.keys(delete1).forEach((item) => {
                        delete1[item] = row[item];
                    });
                    
                    var path="http://localhost:8080/movie/delete";
                    axios.post(path,delete1).then(function(obj){
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

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        const detailform = ref(false);
        const movieadd = ref(false);
        const movieupdate = ref(false);
        
        let idx = -1;

        const Editon = () => {
            var kk = update;
            var path="http://localhost:8080/movie/update";
            axios.post(path,update).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        location.reload();
                        ElMessage.success("更新成功");
                    }
                }
            });

            
            movieupdate.value = false;
            for (const key in update) {
                delete update[key];
            }   
        };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`修改第 ${idx + 1} 行成功`);
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
            });
        };
        // return {
        //     query,
        //     tableData,
        //     pageTotal,
        //     editVisible,
        //     form,
        //     handleSearch,
        //     handlePageChange,
        //     handleDelete,
        //     handleEdit,
        //     saveEdit,
        // };
//     },
// };
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
.btn-column {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 150px;
    height: 200px;
    object-fit: cover;
    border-radius: 4px;
}
</style>
