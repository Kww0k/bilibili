import type {
    InsertTag,
    ParentTagList,
    SimpleTagList,
    UpdateTag
} from "../../../../../type/response/dashboard/system/tag";
import request from "@/net";
import type {BaseResponse} from "../../../../../type/response/response";
import {ElMessage} from "element-plus";

export function listParentTagApi() : Promise<ParentTagList[]> {
    // @ts-ignore
    return request.get("/video/web/tag/listParentTag").then((res : BaseResponse<ParentTagList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function listSimpleTagApi() : Promise<SimpleTagList[]> {
    // @ts-ignore
    return request.get("/video/web/tag/listSimpleTag").then((res : BaseResponse<SimpleTagList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}

export function insertTagApi(insertForm : InsertTag) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.put("/video/admin/tag/insertTag",insertForm).then((res : BaseResponse<null>) => {
        return res
    })
}

export function updateTagApi(updateForm : UpdateTag) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.post("/video/admin/tag/updateTagById",updateForm).then((res : BaseResponse<null>) => {
        return res
    })
}

export function deleteTagApi(id : number) : Promise<BaseResponse<null>> {
    // @ts-ignore
    return request.delete("/video/admin/tag/deleteTagById/" + id).then((res : BaseResponse<null>) => {
        return res
    })
}