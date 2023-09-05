import request from "@/net";
import {ElMessage} from "element-plus";
import type {Card} from "../../../type/card";
import type {BaseResponse} from "../../../type/response";

export function listAdviceApi(): Promise<Card[]> {
    // @ts-ignore
    return request.get('/video/web/video/listAdvice').then((res :BaseResponse<Card[]>) => {
        if (res.code === 200)
            return res.data
        else
            ElMessage.error(res.message)
    })
}