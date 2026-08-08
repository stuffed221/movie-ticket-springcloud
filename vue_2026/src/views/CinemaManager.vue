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
                <el-input v-model="form.cinemaName" placeholder="影院名称" class="handle-input mr10"></el-input>
                
                <el-button type="primary"  icon="Search" @click="detailSearch">搜索</el-button>
                <el-button type="primary"  icon="Search" @click="detail">详细筛选</el-button>
                <el-button type="primary"  icon="Refresh" @click="reseter">重置筛选</el-button>
                <el-button type="info"  icon="right" @click="addcinema">新增影院信息</el-button>

                
                
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="cinemaId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="cinemaName" label="影院名称"></el-table-column>
                <el-table-column prop="cinemaAddress" label="地址"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="EditPen" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
                        <el-button type="text" icon="Delete" class="red" @click="handleDelete(scope.$index,scope.row)">注销</el-button>
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
                <el-form-item label="影院名称">
                    <el-input v-model="form.cinemaName"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.cinemaAddress"></el-input>
                </el-form-item>
                
                
                <!-- <el-form-item label="演员">
                    <el-input v-model="form.cinema"></el-input>

                    
                </el-form-item>
                <el-form-item label="演员">
                    <el-input v-model="form."></el-input>
                </el-form-item> -->
                
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="detailform = false">取 消</el-button>
                    <el-button type="primary" @click="detailSearch">确 定</el-button>
                </span>
            </template>
        </el-dialog>    
            

        <!-- 增加影片 -->
        <el-dialog title="增加影片" v-model="cinemaadd" width="30%">
            
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="影院名称">
                        <el-input v-model="add.cinemaName" />
                    </el-form-item>
                    <el-form-item label="地址">
                        <el-input v-model="add.cinemaAddress" />
                    </el-form-item>
                    
                
                </el-form>
            
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cinemaadd = false">取 消</el-button>
                    <el-button type="primary" @click="addacinema">确 定</el-button>
                </span>
            </template>
        </el-dialog>    


        <!-- 更新表 -->
        <el-dialog title="更新影片" v-model="cinemaupdate" width="30%">
            
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="影院名称">
                        <el-input v-model="update.cinemaName" />
                    </el-form-item>
                    <el-form-item label="地址">
                        <el-input v-model="update.cinemaAddress" />
                    </el-form-item>
                    
                
                </el-form>
        
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="cinemaupdate = false">取 消</el-button>
                <el-button type="primary" @click="Editon">确 定</el-button>
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


        const form = reactive({
            cinemaId:null,
            cinemaName:'',
            cinemaAddress:'',
            
            

        });

        const add = reactive({
            cinemaId:null,
            cinemaName:'',
            cinemaAddress:'',
            
        })

        const update = reactive({
            cinemaId:null,
            cinemaName:'',
            cinemaAddress:'',
            
        })

        const delete1 = reactive({
            cinemaId:null,
            cinemaName:'',
            cinemaAddress:'',
            
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
            var path="http://localhost:8080/cinema/page?npage="+query.pageIndex;
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

        

       
        const addacinema = () => {
            // fetchData(query).then((res) => {
            //     tableData.value = res.list;
            //     pageTotal.value = res.pageTotal || 50;
            // });
            //从后端拿数据
            
                if(add.cinemaName==''||
            add.cinemaAddress==''||
            add.cinemaType==''||
            add.cinemaLanguage==''||
            add.cinemaTime==null||
            add.releaseTime==''||
            add.cinemaPrice==''||
            add.cinemaIntroduction=='')
                {
                    cinemaadd.value = false;
                    for (const key in add) {
                        delete add[key];
                    }   
                    ElMessage.error("信息不完善，添加失败！");
                    
                }
                   
            

            
            var path="http://localhost:8080/cinema/insert";
            axios.post(path,add).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        ElMessage.success("添加成功");
                    }
                }
            });

            
            cinemaadd.value = false;
            for (const key in add) {
                delete add[key];
            }   
            


        };

        const detail = ()=>{
            detailform.value = true;

        }

        const addcinema = ()=>{
            cinemaadd.value = true;

        }

        const handleEdit = (index, row) =>{
            cinemaupdate.value = true;
            idx = index;
            Object.keys(update).forEach((item) => {
                update[item] = row[item];
            });
            

        }

    

        const reseter = ()=>{
            form.cinemaAddress = '',
            form.cinemaName= '',
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
                    
                    var path="http://localhost:8080/cinema/delete";
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
        const cinemaadd = ref(false);
        const cinemaupdate = ref(false);
        
        let idx = -1;

        const Editon = () => {
            var kk = update;
            var path="http://localhost:8080/cinema/update";
            axios.post(path,update).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        location.reload();
                        ElMessage.success("更新成功");
                    }
                }
            });

            
            cinemaupdate.value = false;
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
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
