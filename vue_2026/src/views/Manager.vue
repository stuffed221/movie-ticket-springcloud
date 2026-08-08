<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"><el-icon><Grid /></el-icon></i> 管理员
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            
            <el-table :data="tableData.managerList" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="managerId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="managerName" label="管理员用户名"  align="center"></el-table-column>
                <el-table-column prop="managerSex" label="性别" width="55" align="center"></el-table-column>
                <el-table-column prop="managerPhoneNumber" label="联系电话" align="center"></el-table-column>
                <el-table-column label="状态" width="70" align="center">
                    <template #default="scope">
                        <el-tag :type="scope.row.managerStatus == 1 ? 'success' : 'danger'" effect="dark">
                            {{ scope.row.managerStatus == 1 ? '在线' : '离线' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="EditPen" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
                        <el-button type="text" icon="Delete" class="red" @click="handleDelete()">注销</el-button>
                    </template>
                </el-table-column>
            </el-table>

           <div style="margin-top: 30px;">
            <el-button type="danger" style="display:block;margin:0 auto" @click="exit()">退出登录</el-button>
           </div>
            
        </div>

        <!-- managerName:'',
            managerPassword:'',
            mPhoneNumber:'' -->
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑(留空则不修改)" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="outform.managerName"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="outform.managerSex"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="outform.managerPassword"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="outform.managerPhoneNumber"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
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
        const router= useRouter();

        const editVisible = ref(false);
        
        const tableData = reactive({managerList:null 

        });
        
        // 获取表格数据
        const getData = () => {
            // fetchData(query).then((res) => {
            //     tableData.value = res.list;
            //     pageTotal.value = res.pageTotal || 50;
            // });
            //从后端拿数据
            var userid = localStorage.getItem("userId");
            var path="http://localhost:8080/manager/idsearch?id="+userid;
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        tableData.managerList= result.data;
 
                        
                        
                    }
                }
            });


        };
        getData();

        const outform = reactive({
            managerId:null,
            managerName:'',
            managerSex:'',
            managerPassword:'',
            managerPhoneNumber:''
        })

        const inform = reactive({
            managerId:null,
            managerName:'',
            managerPassword:'',
            managerPhoneNumber:''
        })

        //编辑操作
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            outform.managerId=form.managerId;
            editVisible.value = true;
        };

        // 删除操作
        const handleDelete = () => {
            // 二次确认删除
            ElMessageBox.confirm("确定要注销管理员身份吗？", "提示", {
                type: "warning",
            })
                .then(() => {

                    var ida = localStorage.getItem("userId");
                    var path="http://localhost:8080/manager/delete?id="+idb;
                    axios.post(path,).then(function(obj){
                        if(obj.status == 200){
                            var result = obj.data;
                            if(result.code == 200){
                                
                                localStorage.removeItem("token");
                                localStorage.removeItem("userId");
                                router.push("/loginlog");
                                ElMessage.success("注销成功");

 
                        
                        
                    }
                }
            });

                })
                .catch(() => {});
        };

        // 表格编辑时弹窗和保存
        
        let form = reactive({
            managerId:null,
            managerName:'',
            managerPassword:'',
            managerPhoneNumber:''
        });
        let idx = -1;
        
        const saveEdit = () => {
            
            var path="http://localhost:8080/manager/update";
            axios.post(path,outform).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        
                        ElMessage.success('修改成功');
                        router.push("/index");
 
                        
                        
                    }
                }
            });
            

            editVisible.value = false;
            
            
        };

        const exit = ()=>{
            ElMessageBox.confirm("确定退出吗？", "提示", {
                type: "warning",
            }).then(() => {
                var mid= localStorage.getItem("userId");
                var path = "http://localhost:8080/manager/logout?managerId="+mid;
                axios.post(path,null);
                localStorage.removeItem("token");
                localStorage.removeItem("userId");
                localStorage.removeItem("userName");
                router.push("/loginlog");
                ElMessage.success("退出成功");
                })
                .catch(() => {});
            
        }

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
