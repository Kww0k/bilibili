import type {BaseResponse} from "../../../../../type/response/response";
import request from "@/net";
import {ElMessage} from "element-plus";
import type {VideoList} from "../../../../../type/response/dashboard/video/video";

export function videoListApi(name : String) : Promise<VideoList[]> {
    // @ts-ignore
    return request.get('/file/admin/file/listVideo?name=' + name).then((res :BaseResponse<VideoList>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}