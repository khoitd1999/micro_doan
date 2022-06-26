export interface IWareHouseReceiptDetail {
  id?: any;
  idWare?: any;
  idPro?: any;
  namePro?: any;
  price?: any;
  quantity?: any;
  amount?: any;
}

export class WareHouseReceiptDetail implements IWareHouseReceiptDetail {
  constructor(
    public id?: any,
    public idWare?: string,
    public idPro?: any,
    public namePro?: any,
    public price?: any,
    public quantity?: any,
    public amount?: any,
  ) {
  }
}
