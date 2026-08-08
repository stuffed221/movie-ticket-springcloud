<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!sidebar.collapse"><el-icon><Fold /></el-icon></i>
            <i v-else><el-icon><Expand /></el-icon></i>      
        </div>
        <div class="logo">电影购票管理系统</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 消息中心 -->
                <!-- <div class="btn-bell">
                    <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                        <router-link to="/tabs">
                            <i class="el-icon-bell"><el-icon><Message /></el-icon></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div> -->
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img :src="avatarSrc" @click="triggerUpload" title="点击更换头像" />
                    <input type="file" accept="image/*" ref="fileInput" style="display:none" @change="onFileChange" />
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}}
                        <el-icon><CaretBottom /></el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="user">个人中心</el-dropdown-item>
                            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>
<script>
import { computed, onMounted, ref } from "vue";
import { useSidebarStore } from '../stores/sidebar'
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import axios from "axios";
export default {
    setup() {
        const username = localStorage.getItem("ms_username");
        const message = 2;

        const defaultAvatar = "../assets/img/img.jpg";
        const avatarSrc = ref(localStorage.getItem("ms_avatar") || defaultAvatar);
        const fileInput = ref(null);
        const triggerUpload = () => {
            fileInput.value.click();
        };
        const onFileChange = (e) => {
            const file = e.target.files[0];
            if (!file) return;
            const reader = new FileReader();
            reader.onload = (ev) => {
                const base64 = ev.target.result;
                localStorage.setItem("ms_avatar", base64);
                avatarSrc.value = base64;
                ElMessage.success("头像更新成功");
                location.reload();
            };
            reader.readAsDataURL(file);
        };

        const sidebar = useSidebarStore();
        // 侧边栏折叠
        const collapseChage = () => {
            sidebar.handleCollapse();
        };

        onMounted(() => {
            if (document.body.clientWidth < 1500) {
                collapseChage();
            }
        });

        // 用户名下拉菜单选择事件
        const router = useRouter();
        const handleCommand = (command) => {
            if (command == "loginout") {
                var mid= localStorage.getItem("userId");
                var path = "http://localhost:8080/manager/logout?managerId="+mid;
                axios.post(path,null);
                localStorage.removeItem("token");
                localStorage.removeItem("userId");
                localStorage.removeItem("userName");
                router.push("/loginlog");
                ElMessage.success("退出成功");
            } else if (command == "user") {
                router.push("/manager")
            }
        };

        return {
            sidebar,
            username,
            message,
            avatarSrc,
            fileInput,
            triggerUpload,
            onFileChange,
            collapseChage,
            handleCommand,
        };
    },
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
    background: linear-gradient(90deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
    box-shadow: 0 2px 12px rgba(15, 12, 41, 0.4);
    z-index: 100;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
    font-weight: 700;
    letter-spacing: 2px;
    background: linear-gradient(90deg, #4fc3f7, #b388ff);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>
