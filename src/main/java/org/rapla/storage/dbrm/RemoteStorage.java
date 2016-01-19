/*--------------------------------------------------------------------------*
 | Copyright (C) 2006 ?, Christopher Kohlhaas                               |
 |                                                                          |
 | This program is free software; you can redistribute it and/or modify     |
 | it under the terms of the GNU General Public License as published by the |
 | Free Software Foundation. A copy of the license has been included with   |
 | these distribution in the COPYING file, if not go to www.fsf.org         |
 |                                                                          |
 | As a special exception, you are granted the permissions to link this     |
 | program with every library, which license fulfills the Open Source       |
 | Definition as published by the Open Source Initiative (OSI).             |
 *--------------------------------------------------------------------------*/
package org.rapla.storage.dbrm;

import org.rapla.entities.domain.internal.AppointmentImpl;
import org.rapla.entities.domain.internal.ReservationImpl;
import org.rapla.facade.internal.ConflictImpl;
import org.rapla.framework.RaplaException;
import org.rapla.jsonrpc.common.FutureResult;
import org.rapla.jsonrpc.common.RemoteJsonMethod;
import org.rapla.jsonrpc.common.VoidResult;
import org.rapla.storage.UpdateEvent;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RemoteJsonMethod
public interface RemoteStorage  {
	String USER_WAS_NOT_AUTHENTIFIED = "User was not authentified";
    
    FutureResult<String> canChangePassword();

	FutureResult<VoidResult> changePassword(String username,String oldPassword,String newPassword);
	
	FutureResult<VoidResult> changeName(String username, String newTitle,String newSurename,String newLastname);
	
	FutureResult<VoidResult> changeEmail(String username,String newEmail);
	
	FutureResult<VoidResult> confirmEmail(String username,String newEmail);
    
    FutureResult<UpdateEvent> getResources() throws RaplaException;
    
    /** delegates the corresponding method in the StorageOperator. 
     * @param annotationQuery */
    FutureResult<List<ReservationImpl>> getReservations(@WebParam(name="resources")String[] allocatableIds,@WebParam(name="start")Date start,@WebParam(name="end")Date end, @WebParam(name="annotations")Map<String, String> annotationQuery);

    FutureResult<UpdateEvent> getEntityRecursive(UpdateEvent.SerializableReferenceInfo... infos );

    FutureResult<UpdateEvent> refresh(String lastSyncedTime);
    
	FutureResult<VoidResult> restartServer();

	FutureResult<UpdateEvent> dispatch(UpdateEvent event);
    
//	@ResultType(value=String.class,container=List.class)
//	FutureResult<List<String>> getTemplateNames();
    
	FutureResult<List<String>> createIdentifier(String raplaType, int count);

	FutureResult<List<ConflictImpl>> getConflicts();
	
	FutureResult<BindingMap> getFirstAllocatableBindings(String[] allocatableIds, List<AppointmentImpl> appointments, String[] reservationIds);
	
	FutureResult<List<ReservationImpl>> getAllAllocatableBindings(String[] allocatables, List<AppointmentImpl> appointments, String[] reservationIds);

    FutureResult<Date> getNextAllocatableDate(String[] allocatableIds, AppointmentImpl appointment,String[] reservationIds, Integer worktimeStartMinutes, Integer worktimeEndMinutes, Integer[] excludedDays, Integer rowsPerHour);

	FutureResult<String> getUsername(String userId);

	//void logEntityNotFound(String logMessage,String... referencedIds) throws RaplaException;
    
    class BindingMap
    {
    	Map<String,List<String>> bindings;
    	BindingMap() {
		}
    	public BindingMap(Map<String,List<String>> bindings)
    	{
    		this.bindings = bindings;
    	}
    	
    	public Map<String,List<String>> get() {
			return bindings;
		}
    }
    
}
