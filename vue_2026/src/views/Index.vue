<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img :src="avatarSrc" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{ managerInfo.username }}</div>
                            <div>管理员</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span>{{ managerInfo.loginTime }}</span>
                    </div>
                    
                </el-card>
                <el-card shadow="hover" style="height:252px;">
                    <template #header>
                        <div class="clearfix">
                            <span>喜好指数</span>
                        </div>
                    </template>
                    阿盖尔：神秘特工
                    <el-progress :percentage="97.2" color="#f56c6c"></el-progress>朝云暮雨
                    <el-progress :percentage="96.4" color="#f56c6c"></el-progress>前途海量
                    <el-progress :percentage="90.8" color="#f56c6c"></el-progress>热辣滚烫
                    <el-progress :percentage="88.8" color="#f56c6c"></el-progress>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-user-solid grid-con-icon"><el-icon><User /></el-icon></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num" >{{ Count.userCount }}</div>
                                    <div>注册用户</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-notebook-2 grid-con-icon"><el-icon><Notebook /></el-icon></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ Count.movieCount }}</div>
                                    <div>影视数量</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{ padding: '0px' }">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-sold-out grid-con-icon"><el-icon><SoldOut /></el-icon></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{ Count.orderCount }}</div>
                                    <div>订单数量</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:403px;">
                    <template #header>
                        <div class="clearfix">
                            <span>类别详情</span>
                            <el-button onclick="torentadd()"style="float: right; padding: 3px 0" type="text">添加</el-button>
                        </div>
                        
                    </template>
                    

                       <div class="canvas-container">
                        <div ref="echartRef" style="width:500px;height: 350px;flex:1;"></div>
                        <div ref="echartRef2" style="width:300px;height: 350px;flex:1;"></div>
                       </div>
                    
                    

                    
                </el-card>
            </el-col>
        </el-row>
        <!-- <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
                </el-card>
            </el-col>
        </el-row> -->
    </div>
</template>

<script setup>
//import Schart from "vue-schart";
import { reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { Notebook, SoldOut } from "@element-plus/icons-vue";
import * as echarts from 'echarts';
import {ref,onMounted} from "vue";

import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import { PieChart } from 'echarts/charts';
import { LabelLayout } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';
// import { registerUpdateLifecycle } from "echarts/types/src/echarts.all.js";
// export default {
//     name: "index",
//     setup() {
        function torentadd(){
            router.push("http://localhost:5173/rentadd")
        }

        const defaultAvatar = "../assets/img/img.jpg";
        const avatarSrc = ref(localStorage.getItem("ms_avatar") || defaultAvatar);

        var logintime1 = localStorage.getItem("logintime");

        const router = useRouter();


        var Count = reactive({userCount:0,movieCount:0,orderCount:0});

        var managerInfo = reactive({username:"",loginTime:""});
        
        function getmanagerInfo(){
            var token = localStorage.getItem("token");
            var payloadRaw = token.split(".")[1];
            var payload = JSON.parse(window.atob(payloadRaw));
            managerInfo.loginTime = new Date(payload.iat * 1000).toLocaleString();
            managerInfo.username = localStorage.getItem("userName");
        }
        getmanagerInfo();

        function loadUserCount(){
            var path = "http://localhost:8080/user/count";
            axios.get(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    Count.userCount= result.data;
                    
                }
            })
        }
        loadUserCount();

        function loadMovieCount(){
            var path = "http://localhost:8080/movie/count";
            axios.get(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    Count.movieCount= result.data;
                    
                }
            })
        }
        loadMovieCount();

        function loadOrderCount(){
            var path = "http://localhost:8080/order/count";
            axios.get(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    Count.orderCount= result.data;
                    
                }
            })
        }
        loadOrderCount();

        const Manager = reactive({manager:null});

        const Managername =reactive({name:null});

        function loadManagerName(){
            var userid = localStorage.getItem("loginuser");
            var path="http://localhost:8083/manager/idsearch?id="+userid;
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        Managername.name  = result.data[0];
                        Manager.manager = Managername.name.managerName;
 
                        
                        
                    }
                }
            });
        }
        loadManagerName();


        //图表相关
        
      

        


        //计数测试
        // const typecount = ()=>{
            
        //     var path="http://localhost:8081/video/movie/typenum";
        //     axios.post(path,null).then(function(obj){
        //         if(obj.status == 200){
        //             var result = obj.data;
        //             if(result.code == 200){
                        
        //                 typenum.type1  = result.data[0];
        //                 typenum.type2  = result.data[1];
        //                 typenum.type3  = result.data[2];
        //                 typenum.type4  = result.data[3];
        //                 typenum.type5  = result.data[4];
        //                 typenum.type6  = result.data[5];
        //                 typenum.type7  = result.data[6];
        //                 typenum.type8  = result.data[7];
        //                 typenum.type9  = result.data[8];
        //                 typenum.type10  = result.data[9];
        //                 typenum.type11  = result.data[10];
        //                 typenum.type12  = result.data[11];
                        
 
                        
                        
        //             }
        //         }
        //     });
        // }

        // let a = parseInt(typenum.type1);


        const echartRef = ref(null);
        onMounted(() => {
            var path="http://localhost:8081/video/movie/typenum";
            axios.post(path,null).then(function(obj){
                if(obj.status == 200){
                    var result = obj.data;
                    if(result.code == 200){
                        const myChart = echarts.init(echartRef.value);
  const option = {
    title: {
    text: '类别偏好',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: result.data,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
  };
  myChart.setOption(option);
                        
 
                        
                        
                    }
                }
            });
        


  
});

const echartRef2 = ref(null);
        onMounted(() => {
            
    const myChart1 = echarts.init(echartRef2.value);
  const option = {
    title: {
    text: '影视偏好',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 1048, name: '电影' },
        { value: 735, name: '短剧' }
    ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
  };
  myChart1.setOption(option);
                        
 
                        
                        
                  
        


  
});


        



        

        // const data = reactive([
        //     {
        //         name: "2018/09/04",
        //         value: 1083,
        //     },
        //     {
        //         name: "2018/09/05",
        //         value: 941,
        //     },
        //     {
        //         name: "2018/09/06",
        //         value: 1139,
        //     },
        //     {
        //         name: "2018/09/07",
        //         value: 816,
        //     },
        //     {
        //         name: "2018/09/08",
        //         value: 327,
        //     },
        //     {
        //         name: "2018/09/09",
        //         value: 228,
        //     },
        //     {
        //         name: "2018/09/10",
        //         value: 1065,
        //     },
        // ]);
        // const options = {
        //     type: "bar",
        //     title: {
        //         text: "最近一周各品类销售图",
        //     },
        //     xRorate: 25,
        //     labels: ["周一", "周二", "周三", "周四", "周五"],
        //     datasets: [
        //         {
        //             label: "家电",
        //             data: [234, 278, 270, 190, 230],
        //         },
        //         {
        //             label: "百货",
        //             data: [164, 178, 190, 135, 160],
        //         },
        //         {
        //             label: "食品",
        //             data: [144, 198, 150, 235, 120],
        //         },
        //     ],
        // };
        // const options2 = {
        //     type: "line",
        //     title: {
        //         text: "最近几个月各品类销售趋势图",
        //     },
        //     labels: ["6月", "7月", "8月", "9月", "10月"],
        //     datasets: [
        //         {
        //             label: "家电",
        //             data: [234, 278, 270, 190, 230],
        //         },
        //         {
        //             label: "百货",
        //             data: [164, 178, 150, 135, 160],
        //         },
        //         {
        //             label: "食品",
        //             data: [74, 118, 200, 235, 90],
        //         },
        //     ],
        // };
        // const todoList = reactive([
        //     {
        //         title: "今天要修复100个bug",
        //         status: false,
        //     },
        //     {
        //         title: "今天要修复100个bug",
        //         status: false,
        //     },
        //     {
        //         title: "今天要写100行代码加几个bug吧",
        //         status: false,
        //     },
        //     {
        //         title: "今天要修复100个bug",
        //         status: false,
        //     },
        //     {
        //         title: "今天要修复100个bug",
        //         status: true,
        //     },
        //     {
        //         title: "今天要写100行代码加几个bug吧",
        //         status: true,
        //     },
        // ]);

//         return {
//             name,
//             data,
//             options,
//             options2,
//             todoList,
//             role,
//         };
//     },
// };
</script>

<style scoped>
.canvas-container {
  display: flex;          /* 使用 flex 布局 */
  justify-content: space-between; /* 元素间留有空隙 */
  gap: 10px;             /* 元素间距 */
}

.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
