import { Deserializable } from './Deserializable';

export class resultadoOcorrencia implements Deserializable {
    nomePesquisado: string = '';
    nomeRetornados: string = '';
    sinonimo: string = '';
    baseDeDados: string = '';
    familiaRespectiva: string = '';
    pais: string = '';
    ano: number = 0;
    mes: string = '';
    dia: number = 0;
    latitude: string = '';
    longitude: string = '';
    coordenadaMunicipal: string = '';

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}
