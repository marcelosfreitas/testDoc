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
                console.error('Erro');
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('empresa?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.empresa = response.data.content[0];
            }, function(errReponse) {
                console.error('Erro');
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('empresa/', self.empresa)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function(response) {
                console.log(response);
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
                console.error(errResponse);
            })
    };
    self.buscarTodos(1);

    self.items = ['item1', 'item2', 'item3'];

    self.animationsEnabled = true;

    self.abrirPopUpPesquisaCidade = function (size, parentSelector) {
        var parentElem = parentSelector ? 
        angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaEmpresa.html',
        controller: 'PesquisaEmpresaCtrl',
        controllerAs: 'pesquisaEmpresaCtrl',
        windowClass: 'popUpPesquisaEntidade',
        appendTo: parentElem,
        resolve: {
            items: function () {
            return self.items;
            }
        }
        });

        modalInstance.result.then(function (selectedItem) {
        self.selected = selectedItem;
    }, function () {
        
        });
    };

    self.openComponentModal = function () {
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        component: 'popUpPesquisaEmpresa',
        resolve: {
            items: function () {
            return self.items;
            }
        }
        });

        modalInstance.result.then(function (selectedItem) {
        self.selected = selectedItem;
        }, function () {
        $log.info('modal-component dismissed at: ' + new Date());
        });
    };
});

angular.module('docrotasApp').controller('PesquisaEmpresaCtrl', function ($uibModalInstance, $http, items) {
    var self = this;
    
    self.campoSelecionado;
    self.valorBusca;
    self.cidades = [];

    self.tamanhoMax = 5;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.ok = function () {
        $uibModalInstance.close(self.selected.item);
    };

    self.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

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
            });
    };
});

// Please note that the close and dismiss bindings are from $uibModalInstance.

angular.module('docrotasApp').component('popUpPesquisaEmpresa', {
  templateUrl: 'views/popUpPesquisaEmpresa.html',
  bindings: {
    resolve: '<',
    close: '&',
    dismiss: '&'
  },
  controller: function () {
    var self = this;

    self.$onInit = function () {
      self.items = self.resolve.items;
      self.selected = {
        item: self.items[0]
      };
    };

    self.ok = function () {
      self.close({$value: self.selected.item});
    };

    self.cancel = function () {
      self.dismiss({$value: 'cancel'});
    };
  }
});

