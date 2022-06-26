export interface IBrand {
  id?: any;
  namePro?: any;
  idCat?: any;
}

export class Brand implements IBrand {
  constructor(
    public id?: any,
    public namePro?: any,
    public idCat?: any
  ) {
  }
}
