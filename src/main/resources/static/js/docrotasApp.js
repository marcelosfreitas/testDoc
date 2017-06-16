var app = angular.module('docrotasApp', ['ngRoute', 'ui.bootstrap','ngAnimate', 'ngSanitize']);

app.config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/uf',{
		templateUrl: 'views/cadastro_uf.html',
		controller: 'UfCtrl',
		controllerAs: 'ctrl'
	}).when('/cidade',{
        templateUrl: 'views/cadastro_cidade.html',
        controller: 'CidadeCtrl',
        controllerAs: 'ctrl'
    }).when('/usuario',{
        templateUrl: 'views/cadastro_usuario.html',
        controller: 'UsuarioCtrl',
        controllerAs: 'ctrl'
    }).when('/empresa',{
        templateUrl: 'views/cadastro_empresa.html',
        controller: 'EmpresaCtrl',
        controllerAs: 'ctrl'
    }).when('/pessoa',{
        templateUrl: 'views/cadastro_pessoa.html',
        controller: 'PessoaCtrl',
        controllerAs: 'ctrl'
    }).when('/veiculo',{
        templateUrl: 'views/cadastro_veiculo.html',
        controller: 'VeiculoCtrl',
        controllerAs: 'ctrl'
    }).when('/cte',{
        templateUrl: 'views/cte.html',
        controller: 'CteCtrl',
        controllerAs: 'ctrl'
    });
}]);

angular.module('docrotasApp').controller('ModalDemoCtrl', function ($uibModal, $log, $document) {
  var self = this;
  self.items = ['item1', 'item2', 'item3'];

  self.animationsEnabled = true;

  self.open = function (size, parentSelector) {
    var parentElem = parentSelector ? 
      angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
    var modalInstance = $uibModal.open({
      animation: self.animationsEnabled,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'myModalContent.html',
      controller: 'ModalInstanceCtrl',
      controllerAs: 'modalCtrl',
      size: size,
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

});

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

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
  templateUrl: 'myModalContent.html',
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