import request from "@/net";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";
import type {ExamineList} from "../../../../../type/response/dashboard/home/examine";

export function listUnPassVideo(title : string): Promise<ExamineList[]> {
    // @ts-ignore
    return request.get('/video/admin/video/listUnPassVideo?title=' + title).then((res :BaseResponse<ExamineList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function passVideo(id : number) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.get("/video/admin/video/passVideo/" + id).then((res : BaseResponse<null>) => {
        return res
    })
}

export function deleteVideo(id : number) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.delete("/video/admin/video/deleteVideoById/" + id).then((res : BaseResponse<null>) => {
        return res
    })
}