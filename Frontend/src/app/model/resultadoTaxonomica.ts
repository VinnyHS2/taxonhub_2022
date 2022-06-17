import { Deserializable } from './Deserializable';

export class resultadoTaxonomica implements Deserializable {
    nome_pesquisado: string = '';
    nomes_retornados: string = '';
    sinonimo: string = '';
    base_de_dados: string = '';
    familia_respectiva: string = '';

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}
