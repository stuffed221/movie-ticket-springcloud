<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item>添加租用信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="选择用户" prop="region">
                        <el-select v-model="form.userid" placeholder="请选择">
                            <el-option v-for="user in dataInfo.userList" :key="user.id" :label="user.realname" :value="user.id"></el-option>
                             
                        </el-select>
                    </el-form-item>
                    <el-form-item label="租用类型" prop="resource">
                        <el-radio-group v-model="form.renttype" @change="loadProd()">
                            <el-radio label="电池"></el-radio>
                            <el-radio label="车辆"></el-radio> 
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="选择店铺" prop="region">
                        <el-select v-model="form.storeid" @change="loadProd()"   placeholder="请选择">
                            <el-option v-for="store in dataInfo.storeList" :key="store.id" :label="store.storename" :value="store.id"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item :label="form.msgTtitle" prop="region">
                        <el-select v-model="form.prodid"placeholder="请选择">
                            <el-option   v-if="dataInfo.batteryList !=null" 
                             v-for="battery in dataInfo.batteryList" :key="battery.id" :label="battery.batterytype" :value="battery.id"></el-option>
                             <el-option  v-if="dataInfo.vehicleList!=null"
                            v-for="vehicle in dataInfo.vehicleList" :key="vehicle.id" :label="vehicle.vehicletype" :value="vehicle.id"></el-option>
                        </el-select>
                      

                    </el-form-item>                    
                    
                    <el-form-item label="购买数量" prop="buynum">
                        <el-input v-model="form.buynum"></el-input>
                    </el-form-item>
                   
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">添加租用</el-button>
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
            userid: "",
            storeid: "",
            renttype: "",
            prodid: "", 
            buynum:"",
            msgTtitle:"选择商品"
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
            var path="http://localhost:8084/sale/add";
            axios.post(path,form).then(function(obj){
                if(obj.status==200){
                    var result = obj.data;
                    if(result.code == 200){
                        // router.push("/storeManager");
                        ElMessage.success("租用成功");
                        

                    }
                }
            });
        };
        // onMounted(() => {
        
        // })

        
</script>