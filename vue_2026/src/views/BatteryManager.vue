<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"><el-icon><Grid /></el-icon></i> 电瓶车管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.batterytype" placeholder="电池类型" class="handle-input mr10"></el-input>
                <el-input v-model="query.rentprice" placeholder="借价" class="handle-input mr10"></el-input>
                <el-button type="primary"  icon="Search" @click="handleSearch">搜索</el-button>
                <el-button type="primary"  icon="Search" @click="add()">添加</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="storeid" label="库存序号"></el-table-column>
                <el-table-column prop="batterytype" label="电池类型"></el-table-column>
                <el-table-column prop="capacity" label="容量"></el-table-column>
                <el-table-column prop="rentprice" label="借价"></el-table-column>
                <el-table-column prop="saleprice" label="售价"></el-table-column>
                <el-table-column prop="num" label="库存量"></el-table-column>
                <el-table-column prop="createtime" label="创建时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="EditPen" @click="handleEdict(scope.$index,scope.row)">修改</el-button>
                        <el-button type="text" icon="Delete" class=""red @click="handleDelete(scope.$index,scope.row)">删除</el-button>
                    </template>
                </el-table-column>

        
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
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
        const query = reactive({
            batterytype: "",
            rentprice: null,
            pageIndex: 1,
            pageSize: 2,
            pageTotal:2
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
            var path="http://localhost:8083/shop/battery/page?npage="+query.pageIndex;
            axios.post(path,query).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        var pageInfo = result.data;
                        tableData.value = pageInfo.records;
                        query.pageIndex=pageInfo.current;
                        query.pageTotal=pageInfo.total;
                    }
                }
            });


        };
        getData();

        const router= useRouter();

        function add(){
            router.push("/BatteryAdd")
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

        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    ElMessage.success("删除成功");
                    tableData.value.splice(index, 1);
                })
                .catch(() => {});
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            name: "",
            address: "",
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
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
