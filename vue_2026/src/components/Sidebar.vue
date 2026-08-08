<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="sidebar.collapse" background-color="#1a1a2e"
            text-color="#a5b4fc" active-text-color="#4fc3f7" unique-opened router>
            <template v-for="(item,i) in items">
                <template v-if="item.subs">
                    <el-sub-menu :key="i" :index="item.index">
                        <template #title>
                            <el-icon><component :is="item.icon"></component></el-icon>
                            <span>{{item.title}}</span>
                        </template>
                        <el-menu-item v-for="(subitem,i) in item.subsmenu" :key="i" :index="subitem.index">
                            <el-icon><component :is="subitem.icon"></component></el-icon>
                            {{subitem.title}}
                        </el-menu-item>
                    </el-sub-menu>
                </template>
                <template v-else>
                    <el-menu-item :key="i" :index="item.index">
                        <el-icon><component :is="item.icon"></component></el-icon>
                        <span>{{item.title}}</span>
                    </el-menu-item>
                </template>   
            </template> 
      </el-menu>
    </div>
</template>

<script>
import { computed } from "vue";
import { useSidebarStore } from '../stores/sidebar'
import { useRoute } from "vue-router";
export default {
    setup() {
        const items = [
            {
                icon: "Odometer",
                index: "/index",
                title: "系统首页",
            },
            {
                icon:"Avatar",
                index:"/ManagerManager",
                title:"管理员服务",
            },
            {
                icon: "Film",
                index: "/moviemanager",
                title: "影片管理"
            },{
                icon: "Monitor",
                index: "/cinemamanager",
                title: "影院管理"
            },{
                icon:"Calendar",
                index:"/schedulemanager",
                title:"电影排片",
            },
            {
                icon: "Tickets",
                index: "/orderManager",
                title: "订单管理"
            },
            {
                icon: "Setting",
                index: "3",
                title: "系统设置",
                subs: true,
                subsmenu:[
                    {
                        icon: "Document",
                        index: "/form",
                        title: "表单管理"
                    },
                    {
                        icon: "Shop",
                        index: "/StoreAdd",
                        title: "商店新增"
                    },{
                        icon: "User",
                        index: "/ManagerAdd",
                        title: "管理员新增"
                    }
                    
                    
                ]
            }
        ];

        const route = useRoute();
        const onRoutes = computed(() => {
            return route.path;
        });

        const sidebar = useSidebarStore();

        return {
            items,
            onRoutes,
            sidebar,
        };
    },
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
    background: linear-gradient(180deg, #1a1a2e 0%, #16213e 60%, #0f3460 100%);
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
    background: transparent !important;
}
.sidebar :deep(.el-menu-item),
.sidebar :deep(.el-sub-menu__title) {
    transition: all 0.3s ease;
}
.sidebar :deep(.el-menu-item:hover),
.sidebar :deep(.el-sub-menu__title:hover) {
    background: linear-gradient(90deg, rgba(79, 195, 247, 0.15), rgba(179, 136, 255, 0.15)) !important;
    color: #4fc3f7 !important;
}
.sidebar :deep(.el-menu-item.is-active) {
    background: linear-gradient(90deg, rgba(79, 195, 247, 0.25), rgba(179, 136, 255, 0.1)) !important;
    border-right: 3px solid #4fc3f7;
    color: #4fc3f7 !important;
}
</style>
