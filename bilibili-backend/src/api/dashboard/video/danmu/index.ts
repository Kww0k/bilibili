import request from "@/net";
import type {DanmuList} from "../../../../../type/response/dashboard/video/danmu";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";

export function listDamuApi() : Promise<DanmuList[]> {
    // @ts-ignore
    return request.get("/api/video/admin/danmaku/listDanmaku").then((res : BaseResponse<DanmuList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function updateDamuApi(): Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/api/video/admin/danmaku/updateDanmakuById").then((res : BaseResponse<null>) => {
        return res
    })
}