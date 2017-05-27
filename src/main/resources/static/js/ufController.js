angular.module('docrotasApp').controller('UfCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;
    self.ufs = [];
    self.uf = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];

    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.valuationDate = new Date();
    self.valuationDatePickerIsOpen = false;

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

    self.atualizar = function() {
        if (self.modoGrade == true) {
            self.buscarTodos(1);
        } else {
            if (uf.id) {
                self.buscarPorId(uf.id);
            }
        }       
    };

    self.buscarPorId = function (id) {
        return $http.get('uf?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.uf = response.data.content[0];
            }, function (errResponse) {
                console.error('Erro');
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('uf/', self.uf)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function (errResponse) {
                console.error('Erro');
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.uf = {};
    };

    self.excluir = function (id) {
        $http.delete('uf/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === uf.id) {
                    self.novo();
                }
            }, function (errResponse) {
                console.error('Erro');
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

    self.buscarTodos(1);
});

angular.module('docrotasApp').controller('ErroCtrl', function ($uibModalInstance, $http, msg) {
    var self = this;
    
    self.mensagem = msg;

    self.ok = function () {
        $uibModalInstance.close();
    };
});