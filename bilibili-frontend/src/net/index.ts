import axios from 'axios'
import {ElMessage} from "element-plus";
import {SessionStorageService} from "@/util/storage";
import {STORAGE_PREFIX, TOKEN} from "@/config/cache";


const request = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
    withCredentials: true
})

request.interceptors.request.use(config => {
    let token : string | null = SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`)
    if (token != null)
        config.headers["Authorization"] = "Bearer " + token
    return config;
})

request.interceptors.response.use(
    res  => {
        if (res.status !== 200) {
            if (res.status === 401)
                ElMessage.error("没有权限")
            else if (res.status >= 500)
                ElMessage.error("系统错误")
            else if (res.status === 404)
                ElMessage.error("404找不到请求地址")
            else
                ElMessage.error("请求错误")
        }
        if (res.status === 200)
            return res.data
        return res
    },
    err  => {
        ElMessage.error("请求错误")
        return err
    }
)

export default request