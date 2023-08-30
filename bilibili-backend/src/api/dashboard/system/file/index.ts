import type {BaseResponse} from "../../../../../type/response/response";
import type {FileList} from "../../../../../type/response/dashboard/system/file";
import request from "@/net";
import {ElMessage} from "element-plus";

export function pictureListApi(name : String) : Promise<FileList[]> {
    // @ts-ignore
    return request.get('/file/admin/file/listPicture?name=' + name).then((res :BaseResponse<FileList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}