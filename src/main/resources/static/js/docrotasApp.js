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
    })
}]);