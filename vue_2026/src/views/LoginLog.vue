<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">影视管理系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input id="formname" v-model="form.managerName" >
                        <template #prepend>
                            <el-icon><User /></el-icon><el-button></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input id="formpass" type="password"  v-model="form.managerPassword"
                        @keyup.enter="submitForm()">
                        <template #prepend>
                            <el-icon><Lock /></el-icon><el-button></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="onSubmit()">登录</el-button>
                </div>
                <el-button type="text" @click="manageradd">成为管理员</el-button>
            </el-form>
        </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="注册" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="dialog.managerName"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="dialog.managerPhoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="dialog.managerPassword"></el-input>
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-input v-model="confirmer"></el-input>
                </el-form-item>
                <el-form-item label="邀请码">
                    <el-input v-model="dialog.inviter"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="handleCancel">取 消</el-button>
                    <el-button type="primary" @click="addmanager">确 定</el-button>
                </span>
            </template>
    </el-dialog>
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

        });

        const dialog = reactive({
            managerName:'',
            managerPassword:'',
            managerPhoneNumber:'',
            inviter:''
        })

        const confirmer = ref(null);

        const editVisible = ref(false);

        const manageradd = () =>{
            editVisible.value = true;
        }

   
        const onSubmit = () => {
            var path="http://localhost:8080/manager/login";
            if( form.managerName==''|| form.managerPassword==''){
                
                
                
                ElMessage.error("信息不完善，登录失败");
            }else{
                axios.post(path,form).then(function(obj){
                if(obj.data.code==200){
                    var result = obj.data;
                    var loginToken=result.data.token;
                        var kid= result.data.userId;
                        var name=result.data.userName;
                        if(result.data.role == 0){
                            ElMessage.success("登陆成功");
                        localStorage.setItem("token",loginToken);
                        localStorage.setItem("userId",kid);
                        localStorage.setItem("userName",name);
                        router.push("/index");
                        }
                        else{
                            ElMessage.error("账号已登录，请退出旧设备后重试");
                        }
                    
                }else{
                        
                        ElMessage.error("用户名或密码错误，登陆失败");
                        
                    }
            });

                
                
            }
           
        };

        const addmanager = () =>{
            var kk =dialog;
            var path="http://localhost:8080/manager/insert";
            if( dialog.managerName==''|| dialog.managerPhoneNumber==''|| dialog.managerPassword==''){
                ElMessage.error("信息不完善，添加失败");
                handleCancel();
            }else{if(dialog.managerPassword != confirmer.value){
                ElMessage.error("两次输入的密码不相同，添加失败");
                handleCancel();
            }else{if(dialog.inviter!=12138){
                ElMessage.error("邀请码错误，请联系管理员获取");
                handleCancel();
            }else{
                axios.post(path,dialog).then(function(obj){
                if(obj.status==200){
                    var result = obj.data;
                    if(result.code == 200){
                        ElMessage.success("管理员信息添加成功");
                        handleCancel();
                        

                    }
                }
            });
        }
            }
               
                
            } 
                
            }
            

        const handleCancel = () => {
            dialog.managerName = '';
            dialog.managerPassword = '';
            dialog.managerPhoneNumber = '';
            dialog.inviter = '';
            confirmer.value = '';
            editVisible.value = false;
}
        
        // onMounted(() => {
         
        // })
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/mountain.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>