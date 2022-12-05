    	$(document).ready(function() {
    		$("table").DataTable({
    			order : [ 0, 'desc' ],
    			'aoColumnDefs' : [ {
    				'bSortable' : false,
    				'aTargets' : [ -1 ]
    			} ]
    		});
    	});
