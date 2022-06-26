export interface IWareHouse {
  id?: any;
  nameWar?: string;
  idWar?: any;
  idDis?: any;
  idPro?: any;
  address?: string;
  status?: boolean;
  street?: string;
}

export class WareHouse implements IWareHouse {
  constructor(
    public id?: any,
    public nameWar?: string,
    public idWar?: any,
    public idDis?: any,
    public idPro?: any,
    public address?: string,
    public status?: boolean,
    public street?: string
  ) {
  }
}
