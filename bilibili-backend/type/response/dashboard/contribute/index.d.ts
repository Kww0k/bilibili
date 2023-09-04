export interface InsertVideo {
    title : string,
    description : string,
    previewId : number | null,
    videoId : number | null,
    visibility : string,
    typeId : number | null
}

export interface TagList {
    id: number,
    name: string,
}
