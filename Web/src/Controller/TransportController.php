<?php

namespace App\Controller;

use App\Entity\Billet;
use App\Entity\Categorie;
use App\Entity\CompteBancaire;
use App\Entity\Transport;
use App\Entity\User;
use App\Form\FindTransportType;
use App\Form\PaymentType;
use App\Form\ReservationTransportType;
use App\Form\TransportType;
use App\Repository\BilletRepository;
use App\Repository\TransportRepository;
use DateTime;
use Exception;
use Swift_Mailer;
use Swift_Message;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Serializer\Encoder\JsonEncode;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;
use Symfony\Component\Validator\Constraints\NotBlank;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\JsonResponse;

class TransportController extends AbstractController
{
    private $mailer;
    public function __construct(Swift_Mailer $mailer)
    {
        $this->mailer = $mailer;
    }

    /**
     * @Route("/addTransport", name="add_transport")
     */
    public function addTransport(Request $request): Response
    {
        $em = $this->getDoctrine()->getManager();
        $transport = new Transport();
        $form = $this->createForm(TransportType::class, $transport);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            //upload image
            $uploadFile = $form['image']->getData();
            if($uploadFile != null) {
                $filename = $uploadFile->getClientOriginalName();

                $uploadFile->move($this->getParameter('kernel.project_dir') . '/public/uploads/produit_image', $filename);

                $transport->setImage($filename);
            }
            $em->persist($transport);
            $em->flush();
            return $this->redirectToRoute('read_transport');
        }
        return $this->render('back/transport/addTransport.html.twig', array("formTransport" => $form->createView()));
    }

    /**
     * @Route("/readTransport",name="read_transport")
     */
    public function readTransport()
    {
        $transport= $this->getDoctrine()->getRepository(Transport::class)->findAll();

        return $this->render("back/transport/readTransport.html.twig",array('tabTransport'=>$transport));
    }

    // JSON RESPONSES

    /**
     * @Route("/transport/json", name="TransportJsonAction")
     * @throws ExceptionInterface
     */
    public function transport(): Response
    {
        $transport = $this->getDoctrine()->getManager()
            ->getRepository(Transport::class)->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($transport);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/transport/json/new", name="newTransportJson", methods={"POST"})
     * @throws Exception
     */
    public function newTransportJson(Request $request): JsonResponse
    {
        $transport = new Transport();

        $em = $this->getDoctrine()->getManager();

        $transport->setImage($request->get('image'));
        $transport->setPrix($request->get('prix'));
        $transport->setMarque($request->get('marque'));
        $transport->setMatricule($request->get('matricule'));
        $transport->setModele($request->get('modele'));
        $transport->setNbsiege($request->get('nbsiege'));

        if($request->get('user') == -1){
            $transport->setUser(null);
        }
        else{
            $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('user'));
            $transport->setUser($user);
        }

        $categorie = $this->getDoctrine()->getRepository(Categorie::class)->find($request->get('categorie'));
        $transport->setCategorie($categorie);

        $em->persist($transport);
        $em->flush();

        return new JsonResponse($transport);
    }

    /**
     * @Route("/transport/json/update/{id}", name="updateTransportJson")
     * @throws Exception
     */
    public function updateTransportJson(Request $request, $id): JsonResponse
    {
        $em = $this->getDoctrine()->getManager();

        $transport = $em->getRepository(Transport::class)->find($id);

        $transport->setImage($request->get('image'));
        $transport->setPrix($request->get('prix'));
        $transport->setMarque($request->get('marque'));
        $transport->setMatricule($request->get('matricule'));
        $transport->setModele($request->get('modele'));
        $transport->setNbsiege($request->get('nbsiege'));

        $categorie = $this->getDoctrine()->getRepository(Categorie::class)->find($request->get('categorie'));
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('user'));

        $transport->setCategorie($categorie);
        $transport->setUser($user);


        $em->flush();

        return new JsonResponse($transport);
    }

    /**
     * @Route("/transport/json/{id}", name="TransportIdJson")
     * @throws ExceptionInterface
     */
    public function transportIdJson($id): JsonResponse
    {
        $transport = $this->getDoctrine()->getManager()
            ->getRepository(Transport::class)->find($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($transport);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/transport/json/delete/{id}", name="deleteTransportJsonAction")
     * @throws ExceptionInterface
     */
    public function deleteTransportJsonAction($id): JsonResponse
    {
        $transport = $this->getDoctrine()
            ->getRepository(Transport::class)->find($id);
        $this->getDoctrine()->getManager()->remove($transport);
        $this->getDoctrine()->getManager()->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($transport);
        return new JsonResponse($formatted);
    }

    // JSON RESPONSES DONE !!!

    /**
     * @Route("/editTransport/{id}", name="edit_transport")
     */
    public function editTransport(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $transport = $em->getRepository(Transport::class)->find($id);
        $form = $this->createForm(TransportType::class, $transport);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            //upload image
            $uploadFile = $form['image']->getData();
            if($uploadFile != null) {
                $filename = $uploadFile->getClientOriginalName();

                $uploadFile->move($this->getParameter('kernel.project_dir') . '/public/uploads/produit_image', $filename);

                $transport->setImage($filename);
            }
            $em->flush();
            return $this->redirectToRoute('read_transport');
        }
        return $this->render('back/transport/editTransport.html.twig', array("formTransport" => $form->createView()));
    }

    /**
     * @Route("/deleteTransport/{id}",name="delete_transport")
     */
    public function deleteTransport($id, TransportRepository $repository){
        $transport=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($transport);
        $em->flush();
        return $this->redirectToRoute('read_transport');
    }

    /**
     * @Route("/frontReadTransport",name="front_read_transport")
     */
    public function frontReadTransport(Request $request, PaginatorInterface $paginator) {

        $transports= $this->getDoctrine()->getRepository(Transport::class)->findAllByUserIDNull();
        $form = $this->createForm(FindTransportType::class,null);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $transports = $this->findTransportByCriteria($form);
        }

        $transports = $paginator->paginate(
            $transports, /* query NOT result */
            $request->query->getInt('page', 1),
            3
        );
        return $this->render("front/transport/readTransport.html.twig",array('tabTransport'=>$transports, "formSearchTransport" => $form->createView()));
    }

    /**
     * @Route("/verifBillet/{idTransport}",name="verif_billet")
     */
    public function verifBillet(Request $request,BilletRepository $repository, $idTransport): Response
    {
        $idUserConnected = $this->getParameter('app.userID');
        $idBillet = null;
        $dateReservation = null;
        $form = $this->createForm(ReservationTransportType::class,null,['idBillet' => $idBillet, 'dateReservation' => $dateReservation]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $billet = $repository->find($form['idBillet']->getData());

            if ($billet == null){
                echo "<script type='text/javascript'>alert('This id does not belong to any billet, please verify');</script>";
                return $this->render('front/transport/reserverTransport.html.twig', array("formReservationTransport" => $form->createView()));
            }

            if ($idUserConnected != $billet->getUser()->getId()){
                echo "<script type='text/javascript'>alert('This billet does not belong to you, please verify');</script>";
                return $this->render('front/transport/reserverTransport.html.twig',  array("formReservationTransport" => $form->createView()));
            }

            $dateReservationTimeStamp = $form['dateReservation']->getData()->getTimestamp();
            $dateArriveVol = $billet->getVol()->getDatearrive();
            $dateArriveVolTimeStamp = $dateArriveVol->getTimestamp();

            if ($dateReservationTimeStamp < $dateArriveVolTimeStamp){
                echo "<script type='text/javascript'>alert('Invalid Date, date before date of lending! Please verify');</script>";
                return $this->render('front/transport/reserverTransport.html.twig',  array("formReservationTransport" => $form->createView()));
            } else{
                return $this->redirectToRoute('payment_transport', array("idTransport"=> $idTransport));
            }
        }
        return $this->render('front/transport/reserverTransport.html.twig', array("formReservationTransport" => $form->createView()));
    }

    /**
     * @Route("/paymentTransport/{idTransport}",name="payment_transport")
     */
    public function Pay($idTransport,Request $request)
    {
        $idUserConnected = $this->getParameter('app.userID');
        $em = $this->getDoctrine()->getManager();
        $compte = $em->getRepository(CompteBancaire::class)->findOneByUserId($idUserConnected);

        $form = $this->createForm(PaymentType::class,null);
        $form->handleRequest($request);

        if ($request->isMethod('POST')) {

            if ($form->isValid()) {
                $user = $em->getRepository(User::class)->find($idUserConnected);
                if ($user->getEmail() != $form['email']->getData()){
                    echo "<script type='text/javascript'>alert('Wrong login');</script>";
                    return $this->render('front/transport/paymentTransport.html.twig', ['formPayment' => $form->createView(),
                        'stripe_public_key' => $compte->getStripePublicKey()]);
                }
                $this->payReservationTransport($idUserConnected,$idTransport);
                $transport= $this->getDoctrine()->getRepository(Transport::class)->findByUserID($idUserConnected);
                return $this->render('front/transport/readMyTransports.html.twig' ,array('tabTransportByUser'=>$transport));
            }
        }
        return $this->render('front/transport/paymentTransport.html.twig', ['formPayment' => $form->createView(),
            'stripe_public_key' => $compte->getStripePublicKey()]);
    }

    public function payReservationTransport($idUser,$idTransport)
    {
        $em = $this->getDoctrine()->getManager();
        $transport = $em->getRepository(Transport::class)->find($idTransport);
        $user = $em->getRepository(User::class)->find($idUser);
        $compte = $em->getRepository(CompteBancaire::class)->findOneByUserId($idUser);

        if($transport->getPrix() > $compte->getSolde()) {
            echo "<script type='text/javascript'>alert('Solde insuffisant');</script>";
            return $this->redirectToRoute('front_read_my_transport');
        }
        else {
            $compte->setSolde($compte->getSolde() - $transport->getPrix());
            $transport->setUser($user);
            $em->flush();
            $this->sendMail();
        }
    }

    /**
     * @Route("/readMyTransports",name="front_read_my_transport")
     */
    public function readMyTransports()
    {
        $idUserConnected = $this->getParameter('app.userID');
        $transport= $this->getDoctrine()->getRepository(Transport::class)->findByUserID($idUserConnected);

        return $this->render('front/transport/readMyTransports.html.twig' ,array('tabTransportByUser'=>$transport));
    }

    public function sendMail()
    {
        $message = new Swift_Message('Test email');
        $message->setFrom('svnoclip11@gmail.com');
        $message->setTo('yosrdahmeni6@gmail.com');
        $message->setBody('azerzrz');

        $this->mailer->send($message);

    }


    public function findTransportByCriteria($form)
    {
        $categorie = $form['categorie']->getData()->getNom();
        $prixMin = $form['prixMin']->getData();
        $prixMax = $form['prixMax']->getData();

        if ( $prixMin == null) {
            $prixMin=0;
        }
        if ( $prixMax == null) {
            $prixMax=100000000000000;
        }

        $em = $this->getDoctrine()->getManager();
        $idCategorie = $em->getRepository(Categorie::class)->findOneByName($categorie)->getId();

        $transports = $this->getDoctrine()->getRepository(Transport::class)->findByCriterias($idCategorie, $prixMin, $prixMax);
        return $transports;
    }

    /**
     * @Route("/transportFavori/{idTransport}",name="transport_favori")
     */
    public function transportFavori($idTransport)
    {
        $idUserConnected = $this->getParameter('app.userID');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(User::class)->find($idUserConnected);
        $transport = $em->getRepository(Transport::class)->find($idTransport);

        $user->addTransportsfavori($transport);
        $transport->addUser($user);

        $em->flush();

        $transportsFavori = $user->getTransportsfavoris();

        return $this->render('front/transport/readMyTransports.html.twig' ,array('tabTransportByUser'=>$transportsFavori));
    }


    /**
     * @Route("/frontReadMyTransportFavori",name="front_read_my_transport_favori")
     */
    public function frontReadMyTransportFavori()
    {
        $idUserConnected = $this->getParameter('app.userID');
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(User::class)->find($idUserConnected);
        $transportsFavori = $user->getTransportsfavoris();

        return $this->render('front/transport/readMyTransports.html.twig' ,array('tabTransportByUser'=>$transportsFavori));
    }

}
