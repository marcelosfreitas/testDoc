angular.module('docrotasApp').controller('EmpresaCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;
    self.empresas = [];
    self.empresa = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];
    self.busca = "";

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
        var url = 'empresa?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "razao") {
                url += '&razao=' + self.valorBusca;
            } else if (self.campoSelecionado === "fantasia") {
                url += '&fantasia=' + self.valorBusca;
            } else if (self.campoSelecionado === "cnpj") {
                url += '&cnpj=' + self.valorBusca;
            }  
        }

        return $http.get(url).then(
            function (response) {
                self.empresas = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('empresa?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.empresa = response.data.content[0];
            }, function(errReponse) {
                abrirPopUpErro(errReponse.data.message);
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    };

    self.atualizar = function() {
        if (self.modoGrade == true) {
            self.buscarTodos(1);
        } else {
            if (empresa.id) {
                self.buscarPorId(empresa.id);
            }
        }       
    };

    self.salvar = function () {
        $http.post('empresa/', self.empresa)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function (errResponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.empresa = {};
    };

    self.excluir = function (id) {
        $http.delete('empresa/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === empresa.id) {
                    self.novo();
                }
            }, function (errResponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };
    self.buscarTodos(1);

    self.items = ['item1', 'item2', 'item3'];

    self.abrirPopUpPesquisaCidade = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaCidade.html',
        controller: 'PesquisaCidadeCtrl',
        controllerAs: 'pesquisaCidadeCtrl',
        windowClass: 'popUpPesquisaEntidade',
        resolve: {
            items: function () {
                return self.items;
            }
        }
        });

        modalInstance.result.then(function (cidade) {
            self.empresa.cidade = cidade;
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

angular.module('docrotasApp').controller('PesquisaCidadeCtrl', function ($uibModalInstance, $http, items) {
    var self = this;
    
    self.campoSelecionado;
    self.valorBusca;
    self.cidades = [];

    self.tamanhoMax = 5;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.cidadeSelecionada;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.ok = function () {
        $uibModalInstance.close(self.cidadeSelecionada);
    };

    self.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    self.selecionarCidade = function (cidade) {
        self.cidadeSelecionada = cidade;
        self.ok();
    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;

        var url = 'cidade?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "nome") {
                url += '&nome=' + self.valorBusca;
            } else if (self.campoSelecionado === "codibge") {
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



