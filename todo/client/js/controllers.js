// Default controller for our one page app!
todoApp.controller('TodoController', function($rootScope, $scope, todosFactory) {

  // Todos are just a collection of objects
  $scope.todos = [];
  $scope.isEditable = [];

  // Whenever we load the page, we want to load all of the todos into scope
  todosFactory.getTodos().then(function(data) {
    $scope.todos = data.data;
  });

  // When we create a new todo, send it to the saveTodo method in our factory!
  // Flush input for next todo
  $scope.save = function($event) {
    if ($scope.todoInput && $scope.userEmail && $scope.userType && $scope.priority) { // make sure all fields filled out
      todosFactory.saveTodo({ // write to model
        "todo": $scope.todoInput,
        "isCompleted": $scope.isCompleted,
		"user": $scope.userEmail,
        "userType": $scope.userType,
        "priority": $scope.priority
      }).then(function(data) { // write to view
        $scope.todos.push(data.data);
      });
      $scope.todoInput = ''; // flush input
      $scope.userEmail = '';
      $scope.userType = '';
      $scope.priority = '';
      $scope.isCompleted = '';
      $scope.error = '';
    }
    else {
      if (!$scope.todoInput) {
        $scope.error = 'Please provide a todo!';}
      else if (!$scope.priority) {
        $scope.error = 'Please provide a priority for todo!';}
      else if (!$scope.userEmail) {
        $scope.error = 'Please provide a contact email!';}
      else if (!$scope.userType) {
        $scope.error = 'Please indicate if you are a human or robot!';}
      else {
        $scope.error = 'Unknown error!';}
    }
  };

  // PUT request is how we'll set a todo to completed
  // _t = actual todo we're editing
  $scope.updateStatus = function($event, _id, i) {
    var cbk = $event.target.checked;
    var _t = $scope.todos[i];
    if (_t.priority && _t.userType) { 
      console.log('Checkbox!', _t.todo, _t.priority, _t.user, _t.userType);
      todosFactory.updateTodo({ // write to model
        _id: _id,
        isCompleted: cbk,
        todo: _t.todo,
        user: _t.user,
        priority: _t.priority,
        userType: _t.userType
      }).then(function(data) {
        if (data.data.updatedExisting) { // write to view
          _t.isCompleted = cbk;
        } else {
          alert('Oops something went wrong!');
        }
      });
    }
  };

  // EDIT ----------------------------------
  // change our editing state to NOT editing,
  // and also log what changes are happening
  // TBC: test case! If we weren't able to edit, send an alert!
  $scope.edit = function(i) {
    var _t = $scope.todos[i];
    if (!_t.todo)  {$scope.editError = 'Cannot have unnamed todo!';} // make sure have valid record
    else if (!_t.user) {$scope.editError = 'Please provide contact email!';}
    else {
      console.log('Checkbox!', _t.todo, _t.priority, _t.user, _t.userType);
      todosFactory.updateTodo({ // update model
        _id: _t._id,
        todo: _t.todo,
        user: _t.user,
        userType: _t.userType,
        priority: _t.priority,
        isCompleted: _t.isCompleted
      }).then(function(data) {
        console.log(data.config.data.todo + data.config.data.userType);
        if (data.data.updatedExisting) { // update view
          _t.todo = _t.todo;
          _t.priority = _t.priority;
          _t.user = _t.user;
          _t.userType = _t.userType;
          $scope.isEditable[i] = false;
          $scope.editError = '';
        } else {
          alert('Oops something went wrong!');
        }
      });
    }
  };

  // If we simply want to get rid of a todo permanently, we call the factory's delete method
  // This removes the object from mongodb with matching id
  // When we get a response from the delete method, we change the scope's todo list
  // to NOT include the delete todo (with splice)
  // more at https://docs.mongodb.com/manual/tutorial/remove-documents/
  $scope.delete = function(i) {
    todosFactory.deleteTodo($scope.todos[i]._id).then(function(data) {
      if (data.data) {
        $scope.todos.splice(i, 1); // On success, remove 1 todo at index i in scope
      }
    });
  };

});
