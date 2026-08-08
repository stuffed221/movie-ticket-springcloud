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
                    
                    
                   
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">登录</el-button>
                        
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

   
        const onSubmit = () => {
            var path="http://localhost:8083/manager/page";
            if( form.managerName==''|| form.managerPassword==''){
                ElMessage.error("信息不完善，登录失败");
            }else{
                axios.post(path,form).then(function(obj){
                if(obj.status==200){
                    var result = obj.data;
                    if(result.data.records.length == 0){
                        
                        ElMessage.error("用户名或密码错误，登陆失败");
                        

                    }else{
                        var mid=obj.data.data;
                        var kid= mid.records[0].managerId;
                        // this.$router.push({path:'/manager',query: {id:kid}});
                        ElMessage.success("登陆成功");
                        localStorage.setItem("loginuser",kid);
                        router.push("/manager");

                        
                    }
                }
            });

                
                
            }
           
        };
        
        // onMounted(() => {
         
        // })
</script>