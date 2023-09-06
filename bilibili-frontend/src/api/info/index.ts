import request from "@/net";
import type {BaseResponse} from "../../../type/response";
import type {Info} from "../../../type/info";


export function getInfoApi(id : number): Promise<BaseResponse<Info>> {
    // @ts-ignore
    return request.get('/video/web/video/getCardInfo/' + id).then((res :BaseResponse<Info>) => {
        return res
    })
}
