angular.module('docrotasApp').controller('EmpresaCtrl', ['$http', '$uibModal',function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;
    self.empresas = [];
    self.empresa = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];

    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

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
        return $http.get('empresa?pagina=' + pagina + '&qtd=' + self.tamanhoMax).then(
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

    self.abrirPopUp = function (size, parentSelector) {
        var parentElem = parentSelector ? 
        angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'myModalContent.html',
        controller: 'ModalInstanceCtrl',
        controllerAs: 'modalCtrl',
        size: 'lg',
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
        $log.info('Modal dismissed at: ' + new Date());
        });
    };

    self.openComponentModal = function () {
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        component: 'modalComponent',
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
}]);

angular.module('docrotasApp').controller('ModalInstanceCtrl', function ($uibModalInstance, items) {
  var self = this;
  self.items = items;
  self.selected = {
    item: self.items[0]
  };

  self.ok = function () {
    $uibModalInstance.close(self.selected.item);
  };

  self.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});

// Please note that the close and dismiss bindings are from $uibModalInstance.

angular.module('docrotasApp').component('modalComponent', {
  templateUrl: 'views/myModalContent.html',
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