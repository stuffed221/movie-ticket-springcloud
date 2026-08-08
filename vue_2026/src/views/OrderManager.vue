<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"><el-icon><Grid /></el-icon></i> 订单管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.orderMovieName" placeholder="影片名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="Search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="orderId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="orderNo" label="订单编号" width="160"></el-table-column>
                <el-table-column prop="orderMovieName" label="影片"></el-table-column>
                <el-table-column prop="orderCinemaName" label="影院"></el-table-column>
                <el-table-column prop="orderSeat" label="座位" width="70" align="center"></el-table-column>
                <el-table-column prop="orderAmount" label="金额" width="80" align="center">
                    <template #default="scope">
                        ¥{{ scope.row.orderAmount }}
                    </template>
                </el-table-column>
                <el-table-column label="状态" width="90" align="center">
                    <template #default="scope">
                        <el-tag :type="statusType(scope.row.orderStatus)" effect="dark">
                            {{ statusText(scope.row.orderStatus) }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="orderCreateTime" label="创建时间" width="160"></el-table-column>
                <el-table-column prop="orderUpdateTime" label="更新时间" width="160"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button v-if="scope.row.orderStatus == 1" type="text" icon="CircleCheck" style="color:#67c23a" @click="handleComplete(scope.row)">完成</el-button>
                        <el-button v-if="scope.row.orderStatus == 1" type="text" icon="Money" style="color:#f56c6c" @click="handleRefund(scope.row)">退款</el-button>
                        <el-button v-if="scope.row.orderStatus == 0" type="text" icon="CircleClose" style="color:#e6a23c" @click="handleCancel(scope.row)">取消</el-button>
                        <el-button type="text" icon="Delete" class="red" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="query.pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";

const query = reactive({
    orderMovieName: '',
    pageIndex: 1,
    pageSize: 10,
    pageTotal: 0
});

const tableData = ref([]);

const getData = () => {
    var path = "http://localhost:8080/order/page?npage=" + query.pageIndex;
    axios.post(path, query).then(function(obj) {
        if (obj.status == 200) {
            var result = obj.data;
            if (result.code == 200) {
                var pageInfo = result.data;
                tableData.value = pageInfo.records;
                query.pageIndex = pageInfo.current;
                query.pageTotal = pageInfo.total;
                query.pageSize=pageInfo.size;
            }
        }
    });
};
getData();

const handleSearch = () => {
    query.pageIndex = 1;
    getData();
};

const handlePageChange = (val) => {
    query.pageIndex = val;
    getData();
};

const statusText = (s) => {
    const map = { 0: '待支付', 1: '已支付', 2: '已完成', 3: '已取消', 4: '已退款' };
    return map[s] || '--';
};

const statusType = (s) => {
    const map = { 0: 'warning', 1: 'success', 2: 'primary', 3: 'info', 4: 'danger' };
    return map[s] || '';
};

const handleComplete = (row) => {
    ElMessageBox.confirm("确认核销该订单？", "提示", { type: "warning" })
        .then(() => {
            axios.post("http://localhost:8080/order/complete?orderId=" + row.orderId).then(res => {
                ElMessage.success("核销成功");
                getData();
            });
        })
        .catch(() => {});
};

const handleCancel = (row) => {
    ElMessageBox.confirm("确认取消该订单？", "提示", { type: "warning" })
        .then(() => {
            axios.post("http://localhost:8080/order/cancel?orderId=" + row.orderId + "&reason=管理员取消")
                .then(res => {
                ElMessage.success("已取消");
                getData();
            });
        })
        .catch(() => {});
};

const handleDelete = (row) => {
    ElMessageBox.confirm("确定要删除吗？", "提示", { type: "warning" })
        .then(() => {
            axios.get("http://localhost:8080/order/delete?id=" + row.orderId)
                .then(res => {
                ElMessage.success("删除成功");
                getData();
            });
        })
        .catch(() => {});
};

const handleRefund = (row) => {
    ElMessageBox.confirm("确认退款该订单？", "提示", { type: "warning" })
        .then(() => {
            axios.post("http://localhost:8080/order/refund?orderId=" + row.orderId)
                .then(res => {
                ElMessage.success("退款成功");
                getData();
            });
        })
        .catch(() => {});
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}
.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.mr10 {
    margin-right: 10px;
}
.red {
    color: #ff0000;
}
</style>
