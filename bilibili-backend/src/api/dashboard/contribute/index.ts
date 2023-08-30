import type {InsertVideo, TagList} from "../../../../type/response/dashboard/contribute";
import type {BaseResponse} from "../../../../type/response/response";
import request from "@/net";
import {ElMessage} from "element-plus";

export function insertVideoTest(insertVideo : InsertVideo): Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/video/admin/video/testInsertVideo", insertVideo).then((res : BaseResponse<null>) => {
        return res
    })
}

export function listTagApi() : Promise<TagList[]> {
    // @ts-ignore
    return request.get("/video/web/tag/listTag").then((res : BaseResponse<TagList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}