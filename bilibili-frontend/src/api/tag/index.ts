import {ElMessage} from "element-plus";
import type {TagList} from "../../../type/tag";
import request from "@/net";
import type {BaseResponse} from "../../../type/response";


export function listTagApi(): Promise<TagList[]> {
    // @ts-ignore
    return request.get('/video/web/tag/listTag').then((res :BaseResponse<TagList[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}