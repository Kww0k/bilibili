import request from "@/net";
import type {DanmuList, UpdateDanmu} from "../../../../../type/response/dashboard/video/danmu";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";

export function listDanmuApi() : Promise<DanmuList[]> {
    // @ts-ignore
    return request.get("/video/admin/danmaku/listDanmaku").then((res : BaseResponse<DanmuList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function updateDanmuApi(updateDanmu : UpdateDanmu): Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/video/admin/danmaku/updateDanmakuById", updateDanmu).then((res : BaseResponse<null>) => {
        return res
    })
}

export function deleteDanmuApi(id : number) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.delete("/video/admin/danmaku/deleteDanmakuById/" + id).then((res : BaseResponse<null>) => {
        return res
    })
}