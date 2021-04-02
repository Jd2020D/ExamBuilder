<div class="form-row">
    <div class="col-md-2 mb-2">  
      <form:label path="title">Title:</form:label>
      <form:input  path="title" class="form-control"  />
      <div class="invalid-feedback">
        <form:errors path="title"/>
      </div>
    </div>
    <div class="col-md-1 mb-1">  
      <form:label path="markFrom">Mark:</form:label>
      <form:input  path="markFrom" class="form-control"  />
      <div class="invalid-feedback">
        <form:errors path="markFrom"/>
      </div>
    </div>
    <div class="col-md-3 mb-3">  
      <form:label path="examDay">Exam Day:</form:label>
      <form:input  type="date" path="examDay" class="form-control"  />
      <div class="invalid-feedback">
        <form:errors path="examDay"/>
      </div>
    </div>
    </div>
    <div class="form-row">
      <div class="col-md-2 mb-3">  
        <form:label path="examHour">Exam Hour:</form:label>
        <form:input  type="time" path="examHour"  class="form-control"  />
        <div class="invalid-feedback">
          <form:errors path="examHour"/>
        </div>
      </div>
      <div class="col-md-2 mb-1">  
        <form:label path="duration">Duration:</form:label>
        <form:input   path="duration" class="form-control"  />
        <div class="invalid-feedback">
          <form:errors path="duration"/>
        </div>
      </div>
      <div class="col-md-4 mb-6 ">  
        <div class="row-md-4">
          <form:label path="isExtra">Extra Exam:</form:label>
          <form:errors path="isExtra"/>
          <form:checkbox path="isExtra"/>
  
        </div>
