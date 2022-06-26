export interface IComment {
  id?: any;
  nameCli?: string;
  date?: any;
  comment?: any;
  rate?: any;
  idPro?: any;
}

export class Comment implements IComment {
  constructor(
    public id?: any,
    public nameCli?: string,
    public date?: any,
    public comment?: any,
    public rate?: any,
    public idPro?: any
  ) {
  }
}
