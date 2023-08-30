export interface InsertVideo {
    title : string,
    description : string,
    previewUrl : string,
    videoUrl : string,
    visibility : string,
    typeId : number | null
}

export interface TagList {
    id: number,
    name: string,
}
