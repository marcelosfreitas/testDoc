angular.module('docrotasApp').controller('CidadeCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;
    self.cidades = [];
    self.cidade = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];
    
    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.campoSelecionado;
    self.valorBusca;

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
        var url = 'cidade?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                url += '&id=' + self.valorBusca;
            } else  if (self.campoSelecionado === "nome") {
                url += '&nome=' + self.valorBusca;
            } else if (self.campoSelecionado === "codIGBE") {
                url += '&codibge=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.cidades = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error('Erro');
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('cidade?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.cidade = response.data.content[0];
            }, function(errReponse) {
                console.error('Erro');
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('cidade/', self.cidade)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function(response) {
                console.log(response);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.cidade = {};
    };

    self.excluir = function (id) {
        $http.delete('cidade/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === cidade.id) {
                    self.novo();
                }
            }, function (errResponse) {
                console.error(errResponse);
            })
    };
    self.buscarTodos(1);

    self.atualizar = function() {
        if (self.modoGrade == true) {
            self.buscarTodos(1);
        } else {
            if (cidade.id) {
                self.buscarPorId(cidade.id);
            }
        }       
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
            self.cidade.uf = uf;
        }, function () {
        
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

angular.module('docrotasApp').controller('ErroCtrl', function ($uibModalInstance, $http, msg) {
    var self = this;
    
    self.mensagem = msg;

    self.ok = function () {
        $uibModalInstance.close();
    };
});