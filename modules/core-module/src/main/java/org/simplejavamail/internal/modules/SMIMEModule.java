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
package org.simplejavamail.internal.modules;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimePart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.simplejavamail.api.email.AttachmentResource;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.OriginalSmimeDetails;
import org.simplejavamail.api.internal.outlooksupport.model.OutlookMessage;
import org.simplejavamail.api.internal.smimesupport.builder.SmimeParseResult;
import org.simplejavamail.api.internal.smimesupport.model.AttachmentDecryptionResult;
import org.simplejavamail.api.internal.smimesupport.model.SmimeDetails;
import org.simplejavamail.api.mailer.config.Pkcs12Config;

import java.security.cert.X509Certificate;
import java.util.List;

/**
 * This interface only serves to hide the S/MIME implementation behind an easy-to-load-with-reflection class.
 */
public interface SMIMEModule {

	String NAME = "S/MIME module";

	/**
	 * @return The results of the S/MIME decryption of any compatible encrypted / signed attachments.
	 */
	SmimeParseResult decryptAttachments(@NotNull List<AttachmentResource> attachments, @NotNull OutlookMessage outlookMessage, @Nullable Pkcs12Config pkcs12Config);

	/**
	 * @return The results of the S/MIME decryption of any compatible encrypted / signed attachments.
	 */
	SmimeParseResult decryptAttachments(@NotNull List<AttachmentResource> attachments, @NotNull MimeMessage mimeMessage, @Nullable Pkcs12Config pkcs12Config);

	/**
	 * @return A copy of given original 'true' attachments, with S/MIME encrypted / signed attachments replaced with the actual attachment.
	 */
	@NotNull
	List<AttachmentDecryptionResult> decryptAttachments(@NotNull List<AttachmentResource> attachments, @Nullable Pkcs12Config pkcs12Config, @NotNull OriginalSmimeDetails messageSmimeDetails);

	/**
	 * @return Whether the given attachment is S/MIME signed / encrypted. Defers to {@code SmimeRecognitionUtil.isSmimeAttachment(..)}.
	 */
	boolean isSmimeAttachment(@NotNull AttachmentResource attachment);

	/**
	 * @return The S/MIME mime type and signed who signed the attachment.
	 * <br>
	 * <strong>Note:</strong> the attachment is assumed to be a signed / encrypted {@link jakarta.mail.internet.MimeBodyPart}.
	 */
	@NotNull
	SmimeDetails getSmimeDetails(@NotNull AttachmentResource attachment);

	/**
	 * Delegates to {@link #getSignedByAddress(MimePart)}, where the datasource of the attachment is read completely as a MimeMessage.
	 * <br>
	 * <strong>Note:</strong> the attachment is assumed to be a signed / encrypted {@link jakarta.mail.internet.MimeBodyPart}.
	 */
	@Nullable
	String getSignedByAddress(@NotNull AttachmentResource smimeAttachment);

	/**
	 * @return Who S/MIME signed /encrypted the attachment. This is indicated by the subject of the certificate (whom the certificate was 'issued to').
	 */
	@Nullable
	String getSignedByAddress(@NotNull MimePart mimePart);

	boolean verifyValidSignature(@NotNull MimeMessage mimeMessage, @NotNull OriginalSmimeDetails messageSmimeDetails);

	@NotNull
	MimeMessage signAndOrEncryptEmail(@NotNull final Session session, @NotNull final MimeMessage messageToProtect, @NotNull final Email emailContainingSmimeDetails,
			@Nullable final Pkcs12Config defaultSmimeSigningStore);

	@NotNull
	MimeMessage signMessage(@Nullable Session session, @NotNull MimeMessage message, @NotNull Pkcs12Config pkcs12Config);

	@NotNull
	MimeMessage encryptMessage(@Nullable Session session, @NotNull MimeMessage message, @NotNull X509Certificate certificate);
}
