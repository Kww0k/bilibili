export interface DanmuList {
    id : number,
    content : string,
    color : string,
    time : string
}

export interface UpdateDanmu {
    id : number | null,
    content : string,
    color : string,
    time : string
}