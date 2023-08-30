export interface ParentTagList {
    id: number,
    name: string,
}

export interface SimpleTagList {
    id: number,
    name: string,
    parent: string
}

export interface InsertTag {
    name: string,
    parentId : number | null
}

export interface UpdateTag {
    id: number | null,
    name: string,
    parentId: number | null
}
