export interface IProduct {
  id?: any;
  namePro?: string;
  price?: any;
  idCat?: any;
  idBra?: any;
  description?: any;
  screen?: string;
  os?: boolean;
  ram?: string;
  battery?: string;
  date?: any;
  image?: any;
  status?: boolean;
  rate?: any;
}

export class Product implements IProduct {
  constructor(
    public id?: any,
    public namePro?: string,
    public price?: any,
    public idCat?: any,
    public idBra?: any,
    public description?: any,
    public screen?: string,
    public os?: boolean,
    public ram?: string,
    public battery?: string,
    public date?: any,
    public image?: any,
    public status?: boolean,
    public rate?: any
  ) {
  }
}
