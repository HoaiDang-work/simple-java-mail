/*
 * Copyright © 2009 Benny Bottema (benny@bennybottema.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.simplejavamail.api.internal.smimesupport.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.simplejavamail.api.email.AttachmentResource;
import org.simplejavamail.api.email.OriginalSmimeDetails;
import org.simplejavamail.api.internal.smimesupport.model.AttachmentDecryptionResult;

import java.util.List;

public interface SmimeParseResult {
	@NotNull OriginalSmimeDetails getOriginalSmimeDetails();
	@Nullable AttachmentResource getSmimeSignedEmail();
	@NotNull List<AttachmentDecryptionResult> getDecryptedAttachmentResults();
	@NotNull List<AttachmentResource> getDecryptedAttachments();
}