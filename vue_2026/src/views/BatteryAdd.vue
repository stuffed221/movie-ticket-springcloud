<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item>基本表单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                    <el-form-item label="商店名称" prop="storename">
                        <el-input v-model="form.storename"></el-input>
                    </el-form-item>
                    <el-form-item label="商店地址" prop="address">
                        <el-input v-model="form.address"></el-input>
                    </el-form-item>
                    <el-form-item label="联系人" prop="contactperson">
                        <el-input v-model="form.contactperson"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="phonenumber">
                        <el-input v-model="form.phonenumber"></el-input>
                    </el-form-item>

                    <el-form-item label="日期时间">
                        <el-col :span="11">
                            <el-form-item prop="date1">
                                <el-date-picker type="date" placeholder="选择日期" v-model="form.date1"
                                    style="width: 100%;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        
                    </el-form-item>
                
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
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
import axios from 'axios';
import {useRouter} from "vue-router";

// export default {
//     name: "baseform",
//     setup() {
        const form=reactive({
            storename:'',
            address:'',
            contactperson:'',
            phonenumber:'',
            date1:null,
            date2:null,
        });        

        const rules = {
            name: [
                { required: true, message: "请输入表单名称", trigger: "blur" },
            ],
        };
        
        const router= useRouter();

        const formRef = ref(null);
        
        // 提交
        const onSubmit = () => {
            // 表单校验
            var path = "http://localhost:8083/shop/store/insert";
            axios.post(path,form).then(function(obj){
                if(obj.status == 200)
                    var result = obj.data;
                    if(result.code == 200){

                        router.push("/storeManager");
                        
                    }
            }).then(() => {
                    ElMessage.success("添加成功");
                
                })
            
        };
        // 重置
        const onReset = () => {
            location.reload();
            
        };

//         return {
//             options,
//             rules,
//             formRef,
//             form,
//             onSubmit,
//             onReset,
//         };
//     },
// };
</script>