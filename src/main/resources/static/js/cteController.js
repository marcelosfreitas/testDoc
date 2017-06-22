angular.module('docrotasApp').controller('CteCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;

    self.modoFormulario = true;
    self.modoGrade = false;
    self.cte = {};
    self.ctes = [];

    self.tpEmissaoOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'EPEC SVC'},
        {id : 2, descricao : 'Contigência FSDA'},
        {id : 3, descricao : 'SVC-RS'},
        {id : 4, descricao : 'SVC-SP'}
    ];

    self.tpAmbienteOptions = [
        {id : 0, descricao : 'Produção'},
        {id : 1, descricao : 'Homologação'}
    ];

    self.tpServicoOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'Subcontratação'},
        {id : 2, descricao : 'Resdepacho'},
        {id : 3, descricao : 'Resdepacho Intermediário'},
        {id : 4, descricao : 'Serviço vinculado multimodal'}
    ];

    self.tpCTeOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'Complementar'},
        {id : 2, descricao : 'Anulação'},
        {id : 3, descricao : 'Substituto'}
    ];

    self.novo = function () {
        self.habilitarModoFormulario();
        self.cte = {};
    };

    self.atualizar = function () {

    };

    self.salvar = function () {

    };

    self.excluir = function (id) {
        
    };

    self.habilitarModoGrade = function () {
        self.modoGrade = true;
        self.modoFormulario = false;
    };

    self.habilitarModoFormulario = function () {
        self.modoFormulario = true;
        self.modoFormulario = false;
    };
});