angular.module('docrotasApp').controller('VeiculoCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;

    self.veiculos = [];
    self.veiculo = {};
    self.filtro = {};
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];
    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;
    self.campoSelecionado;
    self.valorBusca;

    self.tipoRodadoOptions = [
        {id : 0, descricao : 'Truck'},
        {id : 1, descricao : 'Toco'},
        {id : 2, descricao : 'Cavalo mecânico'},
        {id : 3, descricao : 'Van'},
        {id : 4, descricao : 'Utilitário'},
        {id : 5, descricao : 'Outros'}
    ];

    self.tipoCarroceriaOptions = [
        {id : 0, descricao : 'Não aplicável'},
        {id : 1, descricao : 'Aberta'},
        {id : 2, descricao : 'Fechado baú'},
        {id : 3, descricao : 'Graneleira'},
        {id : 4, descricao : 'Porta container'},
        {id : 5, descricao : 'Sider'}
    ];

    self.tipoProprietarioOptions = [
        {id : 0, descricao : 'TAC Agregado'},
        {id : 1, descricao : 'TAC Independente'},
        {id : 2, descricao : 'Outros'}
    ];

    var qtd = 15;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.habilitarModoGrade = function () {
        self.modoGrade = true;
        self.modoFormulario = false;
    }

    self.habilitarModoFormulario = function () {
        self.modoGrade = false;
        self.modoFormulario = true;

    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;
        var url = 'veiculo?pagina=' + pagina + '&qtd=' + self.tamanhoMax;
        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                 url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "codFrota") {
                 url += '&codFrota=' + self.valorBusca;
            } else if (self.campoSelecionado === "placa") {
                 url += '&placa=' + self.valorBusca;
            } else if (self.campoSelecionado === "renavam") {
                 url += '&renavam=' + self.valorBusca;
            } else if (self.campoSelecionado === "rntrc") {
                 url += '&rntrc=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.veiculos = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error(errResponse.data.message);
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.atualizar = function() {
        if (self.modoGrade == true) {
            self.buscarTodos(1);
        } else {
            if (veiculo.id) {
                self.buscarPorId(veiculo.id);
            }
        }       
    };

    self.buscarPorId = function (id) {
        return $http.get('veiculo?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.veiculo = response.data.content[0];
            }, function (errResponse) {
                console.error(errResponse.data.message);
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('veiculo/', self.veiculo)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function (errResponse) {
                console.error(errResponse.data.message);
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.veiculo = {};
    };

    self.excluir = function (id) {
        $http.delete('veiculo/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === veiculo.id) {
                    self.novo();
                }
            }, function (errResponse) {
                console.error(errResponse.data.message);
                abrirPopUpErro(errResponse.data.message);
            });
    };

    var abrirPopUpErro = function (msg) {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpErro.html',
        controller: 'ErroCtrl',
        controllerAs: 'erroCtrl',

        resolve: {
            msg: function () {
                return msg;
            }
        }
        });
        modalInstance.result.then(function () {
        }, function () {
     
        });
    };

    self.abrirPopUpPesquisaUF = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaUf.html',
        controller: 'PesquisaUfCtrl',
        controllerAs: 'pesquisaUfCtrl',

        resolve: {
            items: function () {
                return self.items;
            }
        }
        });
        modalInstance.result.then(function (uf) {
            self.veiculo.uf = uf;
        }, function () {
      
        });
    };

    self.abrirPopUpPesquisaPessoa = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaPessoa.html',
        controller: 'PesquisaPessoaCtrl',
        controllerAs: 'pesquisaPessoaCtrl',
        resolve: {
            items: function () {
                return self.items;
            }
        }
        });

        modalInstance.result.then(function (pessoa) {
            self.veiculo.pessoa = pessoa;
        }, function () {
      
        });
    };
    self.buscarTodos(1);
});



angular.module('docrotasApp').controller('PesquisaPessoaCtrl', function ($uibModalInstance, $http, items) {
    var self = this;
 
    self.campoSelecionado;
    self.valorBusca;
    self.pessoas = [];
    self.tamanhoMax = 5;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;
    self.pessoaSelecionada;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.ok = function () {
        $uibModalInstance.close(self.pessoaSelecionada);
    };
    self.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    self.selecionarPessoa = function (pessoa) {
        self.pessoaSelecionada = pessoa;
        self.ok();
    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;
        var url = 'pessoa?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                 url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "razao") {
                 url += '&razao=' + self.valorBusca;
            } else if (self.campoSelecionado === "fantasia") {
                 url += '&fantasia=' + self.valorBusca;
            } else if (self.campoSelecionado === "cpfCnpj") {
                 url += '&cpfCnpj=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.pessoas = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error('Erro');
                abrirPopUpErro(errResponse.data.message);
            });

    };



    var abrirPopUpErro = function (msg) {
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpErro.html',
        controller: 'ErroCtrl',
        controllerAs: 'erroCtrl',
        resolve: {
            msg: function () {
                return msg;
            }
        }
        });

        modalInstance.result.then(function () {

        }, function () {
      
        });

    };

});



angular.module('docrotasApp').controller('PesquisaUfCtrl', function ($uibModalInstance, $http, items) {
    var self = this;
 
    self.campoSelecionado;
    self.valorBusca;
    self.ufs = [];
    self.tamanhoMax = 5;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;
    self.ufSelecionada;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.ok = function () {
        $uibModalInstance.close(self.ufSelecionada);
    };

    self.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    self.selecionarUf = function (uf) {
        self.ufSelecionada = uf;
        self.ok();
    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;
        var url = 'uf?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                 url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "nome") {
                 url += '&descricao=' + self.valorBusca;
            } else if (self.campoSelecionado === "sigla") {
                 url += '&sigla=' + self.valorBusca;
            } else if (self.campoSelecionado === "codIBGE") {
                 url += '&codibge=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.ufs = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error(errResponse.data.message);
                abrirPopUpErro(errResponse.data.message);
            });
    };

    var abrirPopUpErro = function (msg) {
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpErro.html',
        controller: 'ErroCtrl',
        controllerAs: 'erroCtrl',
        resolve: {
            msg: function () {
                return msg;
               }
        }
        });
        modalInstance.result.then(function () {
        }, function () {
      
        });
    };
});



angular.module('docrotasApp').controller('ErroCtrl', function ($uibModalInstance, $http, msg) {
    var self = this;
 
    self.mensagem = msg;

    self.ok = function () {
        $uibModalInstance.close();
    };

});