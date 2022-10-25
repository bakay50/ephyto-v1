<%@ page contentType="text/html" %>
<p>
    ${message(code: "ephyto.mail.header",default:  "NE PAS REPONDRE S'IL VOUS PLAÎT. CECI EST UN MESSAGE DE NOTIFICATION AUTOMATIQUE ENVOYE PAR LE PORTAIL GUCE CI")}
</p>
<p>
    ${raw(mailMsgBody)}
</p>
<br/>
<p>
    ${message(code: "ephyto.mail.msg.footer", default:  """Si vous rencontrez un problème avec le portail GUCE CI, contactez s'il vous plaît le support technique à <a href="mailto:support@guce.gouv.ci">support@guce.gouv.ci</a>
<br/>
<br/>
Equipe GUCE CI
<br/>
<br/>
<i>Les informations contenues dans ce courriel sont uniquement destinées à l'usage de la ou les personne (s) à qui il est adressé ou autrement dirigé.
<br/>
<br/>
Le destinataire doit contrôler la présence de virus dans cet e-mail.
Le Gouvernement n'acceptera aucune responsabilité pour tout dommage causé par un virus transmis par ce courriel.
Si vous n'êtes pas le destinataire ou avez reçu ce message par erreur, s'il vous plaît supprimer le ainsi que toutes les copies de votre système.
</i>""")}
</p>
