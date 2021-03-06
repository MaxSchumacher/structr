/**
 * Copyright (C) 2010-2014 Morgner UG (haftungsbeschränkt)
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.cloud;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.structr.cloud.message.ListSyncables;
import org.structr.cloud.transmission.SingleTransmission;
import org.structr.common.error.FrameworkException;
import org.structr.core.graph.MaintenanceCommand;
import org.structr.rest.resource.MaintenanceParameterResource;

/**
 *
 * @author Axel Morgner
 */
public class ListPagesTestingCommand extends CloudServiceCommand implements MaintenanceCommand {

	private static final Logger logger = Logger.getLogger(ListPagesTestingCommand.class.getName());

	static {

		MaintenanceParameterResource.registerMaintenanceCommand("listPagesTest", ListPagesTestingCommand.class);
	}

	@Override
	public void execute(final Map<String, Object> attributes) throws FrameworkException {

		try {
			System.out.println(CloudService.doRemote(new SingleTransmission(new ListSyncables(null), "admin", "admin", "localhost", 54555), new LoggingListener()));

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public boolean requiresEnclosingTransaction() {
		return true;
	}

	private class LoggingListener implements CloudListener {

		@Override
		public void transmissionStarted() {
			logger.log(Level.INFO, "Transmission started");
		}

		@Override
		public void transmissionFinished() {
			logger.log(Level.INFO, "Transmission finished");
		}

		@Override
		public void transmissionAborted() {
			logger.log(Level.INFO, "Transmission aborted");
		}

		@Override
		public void transmissionProgress(int current, int total) {

			if ((current % 10) == 0) {
				logger.log(Level.INFO, "Transmission progress {0}/{1}", new Object[] { current, total } );
			}
		}
	}
}
