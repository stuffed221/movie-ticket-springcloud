<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item>添加管理员信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="用户名">
                        <el-input v-model="form.managerName" />
                    </el-form-item>
                    <el-form-item label="登陆密码">
                        <el-input v-model="form.managerPassword" />
                    </el-form-item>
                    <el-form-item label="联系电话">
                        <el-input v-model="form.mPhoneNumber" />
                    </el-form-item>
                    <el-form-item label="邀请码">
                        <el-input v-model="form.code" />
                    </el-form-item>
                   
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">添加管理员</el-button>
                        <el-button @click="onReset">重置表单</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import axios from 'axios'
import { useRouter } from "vue-router";

        const router= useRouter();

        const formRef = ref(null);
        const form = reactive({
            managerName:'',
            managerPassword:'',
            mPhoneNumber:'',
            code:null
        });
        const dataInfo = reactive({
            userList: null,
            storeList: null, 
            vehicleList: null,  
            batteryList: null,  
        });
        //加载用户信息
        function loadUserInfo(){
            var path="http://localhost:8084/User/user/list";
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        dataInfo.userList = result.data;
                    }
                }
            });
        }
           //直接调用
           loadUserInfo();
        //加载店铺信息
        function loadStoreInfo(){
            var path="http://localhost:8083/shop/store/list";
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        dataInfo.storeList = result.data;
                    }
                }
            });
        }
        //直接调用
        loadStoreInfo();
        //加载商品信息
        function loadProd(){
            if(form.renttype=="" ||form.storeid==""){
                ElMessage.success("选择租用类别和店铺");
                return;
            }
            var path="";
            if(form.renttype =="电池"){
                form.msgTtitle ="选择电池";
                path="http://localhost:8083/shop/battery/list"  ;  
            }else{
                form.msgTtitle ="选择车辆";
                path="http://localhost:8083/shop/vehicle/list"  ;
            }
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        if(form.renttype =="电池"){
                            dataInfo.vehicleList= null;
                            dataInfo.batteryList=result.data;
                        }else{
                            dataInfo.vehicleList= result.data;
                            dataInfo.batteryList=null;
                        }
                    }
                }
            });
        }
        // 提交
        const onReset = () => {
            // 表单校验
            formRef.value.validate((valid) => {
                if (valid) {
                    console.log(form);
                    ElMessage.success("提交成功！");
                } else {
                    return false;
                }
            });
        };
        // 重置
        const onSubmit = () => {
            var path="http://localhost:8083/manager/insert";
            if( form.managerName==''|| form.managerPassword==''|| form.mPhoneNumber==''){
                ElMessage.error("信息不完善，添加失败");
            }else{if(form.code != 2213){
                ElMessage.error("邀请码错误，添加失败");
            }else{
                axios.post(path,form).then(function(obj){
                if(obj.status==200){
                    var result = obj.data;
                    if(result.code == 200){
                        router.push("/ManagerManager");
                        ElMessage.success("管理员信息添加成功");
                        

                    }
                }
            });
            }
                
                
            }
           
        };
        
        // onMounted(() => {
         
        // })
</script>