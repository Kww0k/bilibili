import request from "@/net";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";
import type {ExamineList} from "../../../../../type/response/dashboard/matter/project";

export function listPassVideo(title : string): Promise<ExamineList[]> {
    // @ts-ignore
    return request.get('/video/admin/video/listPassVideo?title=' + title).then((res :BaseResponse<ExamineList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}