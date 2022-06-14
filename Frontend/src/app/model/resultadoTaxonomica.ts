import { Deserializable } from './Deserializable';

export class resultadoTaxonomica implements Deserializable {
    nomePesquisado: string = '';
    nomeRetornados: string = '';
    sinonimo: string = '';
    baseDeDados: string = '';
    familiaRespectiva: string = '';

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}
