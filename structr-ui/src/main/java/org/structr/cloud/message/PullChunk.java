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
package org.structr.cloud.message;

import java.io.IOException;
import java.util.Iterator;
import org.structr.cloud.CloudConnection;
import org.structr.cloud.CloudService;
import org.structr.cloud.ExportContext;
import org.structr.common.error.FrameworkException;

/**
 *
 * @author Christian Morgner
 */
public class PullChunk extends FileNodeChunk {

	public PullChunk(final String containerId, final int sequenceNumber, final long fileSize) {
		super(containerId, fileSize, sequenceNumber, CloudService.CHUNK_SIZE);
	}

	@Override
	public void onRequest(CloudConnection serverConnection, ExportContext context) throws IOException, FrameworkException {

		final Iterator<FileNodeChunk> chunkIterator = (Iterator<FileNodeChunk>)serverConnection.getValue(containerId);
		if (chunkIterator != null) {

			if (chunkIterator.hasNext()) {

				serverConnection.send(chunkIterator.next());

			} else {

				// chunk iterator is exhausted, remove it from context
				// so that only one FileNodeEndChunk is sent
				serverConnection.removeValue(containerId);

				// return finishing end chunk
				serverConnection.send(new FileNodeEndChunk(containerId, fileSize));
			}
		}
	}

	@Override
	public void onResponse(CloudConnection clientConnection, ExportContext context) throws IOException, FrameworkException {
	}

	@Override
	public void afterSend(CloudConnection conn) {
	}
}
